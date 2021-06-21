package com.pzwpj.projekt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class NewDogePost {
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy, HH:mm:ss")
    private LocalDateTime creationTime;
}
