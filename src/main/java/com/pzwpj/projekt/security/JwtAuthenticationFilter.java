package com.pzwpj.projekt.security;

import com.pzwpj.projekt.config.JwtTokenUtil;
import com.pzwpj.projekt.service.DogeServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.pzwpj.projekt.security.SecurityConstants.HEADER_STRING;
import static com.pzwpj.projekt.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DogeServiceImpl dogeService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting email from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore and also water is wet", e);
            } catch (SignatureException e) {
                logger.error("Authentication Failed. Email or Password not valid.");
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails dogeDetails = dogeService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, dogeDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dogeDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}
