package com.pzwpj.projekt.repository;

import com.pzwpj.projekt.model.DogePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogePostRepository extends JpaRepository<DogePost, Long> {
    List<DogePost> findAllByOrderByCreationTimeDesc();
}

