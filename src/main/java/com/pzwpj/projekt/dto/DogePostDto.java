package com.pzwpj.projekt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public class DogePostDto {
    private long dogePostId;
    private DogeDto dogeDto;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy, HH:mm:ss")
    private LocalDateTime creationTime;
    private List<CommentDto> commentDtos;
    private List<ReactionDto> reactionDtos;
    private Boolean edited;

    public long getDogePostId() {
        return dogePostId;
    }

    public void setDogePostId(long dogePostId) {
        this.dogePostId = dogePostId;
    }

    public DogeDto getDoge() {
        return dogeDto;
    }

    public void setDoge(DogeDto dogeDto) {
        this.dogeDto = dogeDto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public List<CommentDto> getComments() {
        return commentDtos;
    }

    public void setComments(List<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }

    public List<ReactionDto> getReactions() {
        return reactionDtos;
    }

    public void setReactions(List<ReactionDto> reactionDtos) {
        this.reactionDtos = reactionDtos;
    }

    public DogePostDto() {
    }

    public Boolean getEdited() {
        return edited;
    }

    public DogePostDto(long dogePostId, DogeDto dogeDto, String content, LocalDateTime creationTime, List<CommentDto> commentDtos, List<ReactionDto> reactionDtos, Boolean edited) {
        this.dogePostId = dogePostId;
        this.dogeDto = dogeDto;
        this.content = content;
        this.creationTime = creationTime;
        this.commentDtos = commentDtos;
        this.reactionDtos = reactionDtos;
        this.edited = edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }


}
