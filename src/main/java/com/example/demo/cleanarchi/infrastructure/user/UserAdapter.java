package com.example.demo.cleanarchi.infrastructure.user;

import com.example.demo.cleanarchi.domain.model.User;
import com.example.demo.cleanarchi.domain.usecases.UserPort;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

    private final userRepository userRepository;

    public UserAdapter(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        userRepository.save(UserMapper.map(user));
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
