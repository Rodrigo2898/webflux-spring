package com.reactive.reactiveflash.domain.service;

import com.reactive.reactiveflash.domain.document.UserDocument;
import com.reactive.reactiveflash.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserDocument> save(final UserDocument document) {
        return userRepository.save(document)
                .doFirst(() -> log.info("=== try to save a follow document {}", document));
    }
}
