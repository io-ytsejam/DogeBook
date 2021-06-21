package com.pzwpj.projekt.dto;

public class ReactionDto {
    private long id;
    private DogeDto dogeDto;
    private ReactionTypeDto type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DogeDto getDoge() {
        return dogeDto;
    }

    public void setDoge(DogeDto dogeDto) {
        this.dogeDto = dogeDto;
    }

    public ReactionTypeDto getType() {
        return type;
    }

    public void setType(ReactionTypeDto type) {
        this.type = type;
    }

    public ReactionDto(long id, DogeDto dogeDto, ReactionTypeDto type) {
        this.id = id;
        this.dogeDto = dogeDto;
        this.type = type;
    }

    public ReactionDto() {
    }
}