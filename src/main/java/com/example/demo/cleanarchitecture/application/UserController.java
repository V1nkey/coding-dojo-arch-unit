package com.example.demo.cleanarchitecture.application;

import com.example.demo.cleanarchitecture.domain.usecases.user.RegisterUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RegisterUser registerUser;

    public UserController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(UserRequest userRequest) {
        registerUser.execute(UserMapper.map(userRequest));
        return ResponseEntity.ok().build();
    }
}
