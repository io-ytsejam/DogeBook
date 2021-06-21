package com.pzwpj.projekt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@Entity
public class Doge {
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String password;
    @Column(unique = true)
    private String username;
    @Id
    @GeneratedValue
    private Long dogeId;

    public Doge() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDogeId() {
        return dogeId;
    }

    public void setDogeId(Long dogeId) {
        this.dogeId = dogeId;
    }

    public Long getId() {
        return dogeId;
    }

    public void setId(Long dogeId) {
        this.dogeId = dogeId;
    }

    public Collection<? extends GrantedAuthority> getRole() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public Doge(String firstname, String lastname, String password, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.username = username;
    }
}
