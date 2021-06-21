package com.pzwpj.projekt.service;

import com.pzwpj.projekt.config.JwtTokenUtil;
import com.pzwpj.projekt.dto.DogeLoginDto;
import com.pzwpj.projekt.dto.DogeRegistrationDto;
import com.pzwpj.projekt.exceptions.DogeNotFound;
import com.pzwpj.projekt.exceptions.DuplicatedDoge;
import com.pzwpj.projekt.model.Doge;
import com.pzwpj.projekt.repository.DogeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DogeServiceImpl implements DogeService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DogeRepository dogeRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public String signIn(DogeLoginDto dogeLoginDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dogeLoginDto.getUsername(),
                            dogeLoginDto.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new DogeNotFound("There is no such doge in this book");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var optDoge = this.loadDogeByUsername(dogeLoginDto.getUsername());

        if (optDoge.isEmpty())
            throw new DogeNotFound("There is no such doge in this book");

        return jwtTokenUtil.generateToken(optDoge.get());
    }

    @Override
    public void signUp(DogeRegistrationDto dogeRegistrationDto) throws DuplicatedDoge {
        var username = dogeRegistrationDto.getUsername();
        var firstname = dogeRegistrationDto.getFirstname();
        var lastname = dogeRegistrationDto.getLastname();
        var password = dogeRegistrationDto.getPassword();
        var doge = new Doge(firstname, lastname, encoder.encode(password), username);

        if (dogeRepository.findByUsername(username).isPresent())
            throw new DuplicatedDoge("User with given username already exists");

        dogeRepository.save(doge);
    }

    @Override
    public Boolean isLogged(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return jwtTokenUtil.validateToken(token, this.loadUserByUsername(username));
    }

    @Override
    public Optional<Doge> loadDogeByUsername(String username) {
        return dogeRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var optionalDoge = dogeRepository.findByUsername(username);
        if (optionalDoge.isEmpty()) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        var doge = optionalDoge.get();

        return new org.springframework.security.core.userdetails.User(
                doge.getUsername(),
                doge.getPassword(),
                doge.getRole()
        );
    }
}
