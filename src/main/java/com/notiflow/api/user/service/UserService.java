package com.notiflow.api.user.service;

import com.notiflow.api.user.dto.CreateUserRequest;
import com.notiflow.api.user.exception.EmailAlreadyExistsException;
import com.notiflow.api.user.exception.UserNotFoundException;
import com.notiflow.api.user.mapper.UserMapper;
import com.notiflow.api.user.model.User;
import com.notiflow.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            log.warn("User already exists");
            throw new EmailAlreadyExistsException(createUserRequest.email());
        }

        User user = userRepository.save(UserMapper.toEntity(createUserRequest));
        log.info("User successfully created: userId={}", user.getId());

        return user;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("User with this id does not exist: userId={}", id);
                    return new UserNotFoundException(id);
                });
    }
}
