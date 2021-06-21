package com.pzwpj.projekt.dto;

import java.time.LocalDateTime;

public class CommentDto {
    private Long id;
    private DogeDto dogeDto;
    private String content;
    private LocalDateTime creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CommentDto(Long id, DogeDto dogeDto, String content, LocalDateTime creationTime) {
        this.id = id;
        this.dogeDto = dogeDto;
        this.content = content;
        this.creationTime = creationTime;
    }

    public CommentDto() {
    }
}
