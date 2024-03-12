package com.example.demo.cleanarchitecture.infrastructure;

import com.example.demo.cleanarchitecture.domain.model.User;
import com.example.demo.cleanarchitecture.domain.usecases.UserPort;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

    private final UserRepository userRepository;

    public UserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }
}
