package com.pzwpj.projekt.controllers;

import com.pzwpj.projekt.dto.*;
import com.pzwpj.projekt.model.Comment;
import com.pzwpj.projekt.model.DogePost;
import com.pzwpj.projekt.service.DogePostingService;
import com.pzwpj.projekt.service.DogeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posting")
public class Posting {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DogePostingService dogePostingService;

    @Autowired
    DogeService dogeService;

    @GetMapping(value = "/get-all")
    ResponseEntity<List<DogePostDto>> getAll() {
        List<DogePostDto> dogePostDtos = dogePostingService
                .getAllDogePosts()
                .stream().map(dp -> modelMapper.map(dp, DogePostDto.class))
                .toList();

        return new ResponseEntity<>(dogePostDtos, HttpStatus.OK);
    }

    @PutMapping(value = "/create")
    ResponseEntity<DogePostDto> createDogePost(@RequestBody DogePostDto dogePostDto, @RequestHeader("Authorization") String token) {
        DogePost dogePost = modelMapper.map(dogePostDto, DogePost.class);
        var response = modelMapper.map(dogePostingService.createDogePost(dogePost, token), DogePostDto.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/react")
    ResponseEntity<Object> reactToDogePost(@RequestBody AddReactionDto addReactionDto, @RequestHeader("Authorization") String token) {

        try {
            var dogePost = dogePostingService.reactToDogePost(addReactionDto, token);
            var dogePostDto = modelMapper.map(dogePost, DogePostDto.class);

            return new ResponseEntity<>(dogePostDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/react")
    ResponseEntity<Object> deleteReactionToDogePost(@RequestBody DeleteReactionDto deleteReactionDto, @RequestHeader("Authorization") String token) {

        try {
            var dogePost = dogePostingService.deleteReactionToDogePost(deleteReactionDto, token);
            var dogePostDto = modelMapper.map(dogePost, DogePostDto.class);

            return new ResponseEntity<>(dogePostDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/edit-post")
    ResponseEntity<Object> editDogePost(@RequestBody EditDogePostDto editDogePostDto, @RequestHeader("Authorization") String token) {
        try {
            var dogePost = dogePostingService.editDogePost(editDogePostDto, token);
            var dogePostDto = modelMapper.map(dogePost, DogePostDto.class);

            return new ResponseEntity<>(dogePostDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete-post")
    ResponseEntity<Object> deleteDogePost(@RequestBody DeleteDogePostDto deleteDogePostDto, @RequestHeader("Authorization") String token) {
        try {
            dogePostingService.deleteDogePost(deleteDogePostDto, token);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/add-comment")
    ResponseEntity<Object> addCommentToDogePost(@RequestBody AddCommentDto addCommentDto, @RequestHeader("Authorization") String token) {
        try {
            var dogeComment = dogePostingService.addCommentToDogePost(addCommentDto, token);
            var dogeCommentDto = modelMapper.map(dogeComment, CommentDto.class);

            return new ResponseEntity<>(dogeCommentDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
