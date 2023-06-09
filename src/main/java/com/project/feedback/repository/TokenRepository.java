package com.project.feedback.repository;

import com.project.feedback.infra.outgoing.jpa.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByAccessToken(String accessToken);

}
