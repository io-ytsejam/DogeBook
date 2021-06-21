package com.pzwpj.projekt.controllers;

import com.pzwpj.projekt.config.JwtTokenUtil;
import com.pzwpj.projekt.dto.DogeDto;
import com.pzwpj.projekt.repository.DogeRepository;
import com.pzwpj.projekt.service.DogeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doge")
public class Doge {
    @Autowired
    DogeServiceImpl dogeService;

    @Autowired
    DogeRepository dogeRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(name = "/")
    ResponseEntity<DogeDto> getDoge(@RequestHeader("Authorization") String token) {

        var optDoge = dogeService.loadDogeByUsername(jwtTokenUtil.getUsernameFromToken(token.split(" ")[1]));

        if (optDoge.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        var doge = optDoge.get();
        var username = doge.getUsername();
        var firstname = doge.getFirstname();
        var lastname = doge.getLastname();

        DogeDto dogeDto = new DogeDto(firstname, lastname, username);

        return new ResponseEntity<>(dogeDto, HttpStatus.OK);
    }
}
