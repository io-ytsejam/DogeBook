package com.pzwpj.projekt.controllers;


import com.pzwpj.projekt.dto.DogeLoginDto;
import com.pzwpj.projekt.dto.DogeRegistrationDto;
import com.pzwpj.projekt.dto.TokenDto;
import com.pzwpj.projekt.exceptions.DogeNotFound;
import com.pzwpj.projekt.exceptions.DuplicatedDoge;
import com.pzwpj.projekt.service.DogeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/authorization")
public class Authorization {
    @Autowired
    DogeServiceImpl dogeService;

    Logger logger = LoggerFactory.getLogger(Authorization.class);

    @PostMapping(value = "/sign-in")
    @ResponseBody
    ResponseEntity<String> signIn(@RequestBody DogeLoginDto dogeLoginDto) {
        try {
            return new ResponseEntity<>(dogeService.signIn(dogeLoginDto), HttpStatus.OK);
        } catch (DogeNotFound dnf) {
            logger.info(dnf.getMessage());
            return new ResponseEntity<>(dnf.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/sign-up")
    @ResponseBody
    public ResponseEntity<String> signUp(@Valid @RequestBody DogeRegistrationDto dogeData) {
        try {
            dogeService.signUp(dogeData);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DuplicatedDoge dd) {
            logger.info(dd.getMessage());
            return new ResponseEntity<>(dd.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/is-token-valid")
    @ResponseBody
    public ResponseEntity<String> isTokenValid(@Valid @RequestBody TokenDto token) {
        try {
            return new ResponseEntity<>(dogeService.isLogged(token.getValue()).toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
