package com.pzwpj.projekt.dto;

public class AddReactionDto {
    private Long dogePostId;
    private String type;

    public Long getDogePostId() {
        return dogePostId;
    }

    public void setDogePostId(Long dogePostId) {
        this.dogePostId = dogePostId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
