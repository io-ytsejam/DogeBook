package com.pzwpj.projekt.service;

import com.pzwpj.projekt.dto.DogeLoginDto;
import com.pzwpj.projekt.dto.DogeRegistrationDto;
import com.pzwpj.projekt.exceptions.DogeNotFound;
import com.pzwpj.projekt.exceptions.DuplicatedDoge;
import com.pzwpj.projekt.model.Doge;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface DogeService extends UserDetailsService {
    String signIn(DogeLoginDto dogeLoginDto) throws DogeNotFound;
    void signUp(DogeRegistrationDto dogeRegistrationDto) throws DuplicatedDoge;
    Boolean isLogged(String token) throws Exception;
    Optional<Doge> loadDogeByUsername(String username);
}
