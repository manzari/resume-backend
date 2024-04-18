package me.manzari.resume.controller;

import me.manzari.resume.model.AuthenticationResponse;
import me.manzari.resume.service.AuthenticationService;
import me.manzari.resume.model.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    @CrossOrigin
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AppUser request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AppUser request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}