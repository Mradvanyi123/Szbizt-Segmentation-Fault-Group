package hu.bme.sfg.catalogbackend.application.controller;

import hu.bme.sfg.catalogbackend.application.dto.RegisterDto;
import hu.bme.sfg.catalogbackend.application.dto.UserDto;
import hu.bme.sfg.catalogbackend.application.service.UserService;
import hu.bme.sfg.catalogbackend.util.UserException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto user) {
        try {
            return ResponseEntity.ok(userService.register(user));
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<UserDto> getMyInfo(Principal principal) {
        return ResponseEntity.ok(userService.getMyInfo(principal));
    }
}

