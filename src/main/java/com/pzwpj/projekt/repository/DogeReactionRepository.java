package com.pzwpj.projekt.repository;

import com.pzwpj.projekt.model.Doge;
import com.pzwpj.projekt.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DogeReactionRepository extends JpaRepository<Reaction, Long> {
     List<Reaction> findAllByDoge(Doge doge);
}
