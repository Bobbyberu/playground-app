package com.playground.repository;

import com.playground.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, String> {

    Optional<VerificationToken> findByToken(String token);

}