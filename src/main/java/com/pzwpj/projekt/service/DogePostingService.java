package com.pzwpj.projekt.service;

import com.pzwpj.projekt.config.JwtTokenUtil;
import com.pzwpj.projekt.dto.*;
import com.pzwpj.projekt.exceptions.DogeNotFound;
import com.pzwpj.projekt.exceptions.DogeNotPermitted;
import com.pzwpj.projekt.exceptions.DogePostNotFound;
import com.pzwpj.projekt.exceptions.DogeReactionNotFound;
import com.pzwpj.projekt.model.Comment;
import com.pzwpj.projekt.model.DogePost;
import com.pzwpj.projekt.model.Reaction;
import com.pzwpj.projekt.model.ReactionType;
import com.pzwpj.projekt.repository.DogePostRepository;
import com.pzwpj.projekt.repository.DogeReactionRepository;
import com.pzwpj.projekt.repository.DogeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogePostingService {
    @Autowired
    DogePostRepository dogePostRepository;

    @Autowired
    DogeRepository dogeRepository;

    @Autowired
    DogeReactionRepository dogeReactionRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public List<DogePost> getAllDogePosts() {
        return dogePostRepository.findAllByOrderByCreationTimeDesc();
    }

    public DogePost createDogePost(DogePost dogePost, String token) throws DogeNotFound {
        var username = jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
        var optDoge = dogeRepository.findByUsername(username);

        if (optDoge.isEmpty())
            throw new DogeNotFound("Doge not found");
        dogePost.setDoge(optDoge.get());

        return dogePostRepository.save(dogePost);
    }

    public DogePost reactToDogePost(AddReactionDto addReactionDto, String token) throws DogePostNotFound {
        var id = addReactionDto.getDogePostId();
        var type = addReactionDto.getType();
        var optDogePost = dogePostRepository.findById(id);

        if (optDogePost.isEmpty())
            throw new DogePostNotFound("Doge post with given ID was not found");

        var dogePost = optDogePost.get();
        var username = jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
        var optDoge = dogeRepository.findByUsername(username);

        if (optDoge.isEmpty())
            throw new DogeNotFound("Doge was not found");

        var doge = optDoge.get();

        var reaction = new Reaction(ReactionType.valueOf(type), dogePost, doge);

        dogePost.getReactions().add(reaction);
        dogePost.setReactions(dogePost.getReactions());

        dogePostRepository.save(dogePost);

        return dogePost;
    }

    public DogePost deleteReactionToDogePost(DeleteReactionDto deleteReactionDto, String token) throws DogePostNotFound {
        var id = deleteReactionDto.getDogePostId();
        var optDogePost = dogePostRepository.findById(id);

        if (optDogePost.isEmpty())
            throw new DogePostNotFound("Doge post with given ID was not found");

        var dogePost = optDogePost.get();

        var username = jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
        var optDoge = dogeRepository.findByUsername(username);

        if (optDoge.isEmpty())
            throw new DogeNotFound("Doge was not found");

        var doge = optDoge.get();
        var optReaction = dogeReactionRepository
                .findAllByDoge(doge)
                .stream()
                .findFirst();

        if (optReaction.isEmpty())
            throw new DogeReactionNotFound("Doge did not reacted for given doge post");

        var reaction = optReaction.get();

        dogeReactionRepository.delete(reaction);

        return dogePost;
    }

    public DogePost editDogePost(EditDogePostDto editDogePostDto, String token) {
        var dogePostId = editDogePostDto.getDogePostId();
        var content = editDogePostDto.getContent();
        var optDogePost = dogePostRepository.findById(dogePostId);

        if (optDogePost.isEmpty())
            throw new DogePostNotFound("Doge post with given ID was not found");

        var dogePost = optDogePost.get();
        var username = jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
        var optDoge = dogeRepository.findByUsername(username);

        if (optDoge.isEmpty())
            throw new DogeNotFound("Doge was not found");

        var doge = optDoge.get();

        if (!doge.getUsername().equals(dogePost.getDoge().getUsername()))
            throw new DogeNotPermitted("Dogemission denied. Doge cannot edit post if they not created it");

        dogePost.setEdited(true);
        dogePost.setContent(content);

        return dogePostRepository.save(dogePost);
    }

    public void deleteDogePost(DeleteDogePostDto deleteDogePostDto, String token) {
        var dogePostId = deleteDogePostDto.getDogePostId();
        var optDogePost = dogePostRepository.findById(dogePostId);

        if (optDogePost.isEmpty())
            throw new DogePostNotFound("Doge post with given ID was not found");

        var dogePost = optDogePost.get();
        var username = jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
        var optDoge = dogeRepository.findByUsername(username);

        if (optDoge.isEmpty())
            throw new DogeNotFound("Doge was not found");

        var doge = optDoge.get();

        if (!doge.getUsername().equals(dogePost.getDoge().getUsername()))
            throw new DogeNotPermitted("Dogemission denied. Doge cannot delete post if they not created it");

        dogePostRepository.delete(dogePost);
    }

    public Comment addCommentToDogePost(AddCommentDto addCommentDto, String token) {
        var dogePostId = addCommentDto.getDogePostId();
        var content = addCommentDto.getContent();
        var creationTime = addCommentDto.getCreationTime();
        var optDogePost = dogePostRepository.findById(dogePostId);

        if (optDogePost.isEmpty())
            throw new DogePostNotFound("Doge post with given ID was not found");

        var dogePost = optDogePost.get();
        var username = jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]);
        var optDoge = dogeRepository.findByUsername(username);

        if (optDoge.isEmpty())
            throw new DogeNotFound("Doge was not found");

        var doge = optDoge.get();
        var comment = new Comment(content, creationTime, dogePost, doge);

        dogePost.getComments().add(comment);
        dogePostRepository.save(dogePost);

        return comment;
    }
}
