package com.example.demo.cleanarchi.application;

import com.example.demo.cleanarchi.domain.usecases.registration.RegisterUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final RegisterUser registerUser;

    public UserController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody UserRequest userRequest) {
        try {
            registerUser.execute(UserMapper.map(userRequest));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().build();
    }
}
