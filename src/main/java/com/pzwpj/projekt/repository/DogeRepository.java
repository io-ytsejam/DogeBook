package com.pzwpj.projekt.repository;

import com.pzwpj.projekt.model.Doge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DogeRepository extends JpaRepository<Doge, Long> {
    Optional<Doge> findByUsername(String username);
}
