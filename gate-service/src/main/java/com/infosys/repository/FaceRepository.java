package com.infosys.repository;

import com.infosys.model.Face;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaceRepository extends JpaRepository<Face, Long> {
    @Bean
    Optional<Face> findById(Long id);
}