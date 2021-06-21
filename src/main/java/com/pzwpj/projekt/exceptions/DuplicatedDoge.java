package com.pzwpj.projekt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicatedDoge extends RuntimeException {
    public DuplicatedDoge(String message) {
        super(message);
    }
}
