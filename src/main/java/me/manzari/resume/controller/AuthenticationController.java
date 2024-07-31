package me.manzari.resume.controller;

import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.AuthenticationResponse;
import me.manzari.resume.service.AuthenticationService;
import me.manzari.resume.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    private final TrackingService trackingService;

    public AuthenticationController(AuthenticationService authService, TrackingService trackingService) {
        this.authService = authService;
        this.trackingService = trackingService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AppUser request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AppUser request) {
        AuthenticationResponse response = authService.authenticate(request);
        if (response.token() != null) {
            try {
                trackingService.track("login", "/login", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            } catch (InterruptedException ignored) {
            }
        }
        return ResponseEntity.ok(response);
    }
}