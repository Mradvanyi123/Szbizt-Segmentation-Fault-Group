package hu.bme.sfg.catalogbackend.application.controller;

import hu.bme.sfg.catalogbackend.application.dto.UserDto;
import hu.bme.sfg.catalogbackend.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody UserDto user) {
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/register")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto user) {
        return ResponseEntity.notFound().build();
    }
}

