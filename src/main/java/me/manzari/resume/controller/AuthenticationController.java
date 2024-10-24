package me.manzari.resume.controller;

import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.AuthenticationResponse;
import me.manzari.resume.service.Action;
import me.manzari.resume.service.ActionService;
import me.manzari.resume.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    private final ActionService actionService;

    public AuthenticationController(AuthenticationService authService, ActionService actionService) {
        this.authService = authService;
        this.actionService = actionService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AppUser request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AppUser request) {
        AuthenticationResponse response = authService.authenticate(request);
        if (response.token() != null) {
            actionService.process(Action.Login, "/login", request);
        }
        return ResponseEntity.ok(response);
    }
}