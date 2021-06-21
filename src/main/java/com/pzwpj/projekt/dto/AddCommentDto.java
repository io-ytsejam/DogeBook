package com.pzwpj.projekt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AddCommentDto {
    private Long dogePostId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy, HH:mm:ss")
    private LocalDateTime creationTime;
    private String content;

    public Long getDogePostId() {
        return dogePostId;
    }

    public void setDogePostId(Long dogePostId) {
        this.dogePostId = dogePostId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
