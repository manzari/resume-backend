package me.manzari.resume.controller;


import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.Role;
import me.manzari.resume.repository.AppUserRepository;
import me.manzari.resume.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("/users")
    @CrossOrigin
    public ResponseEntity<List> getUsers() {
        final List<AppUser> appUsers = this.appUserRepository.findAll();
        return ResponseEntity.ok(appUsers);
    }

    @DeleteMapping("/user/{id}")
    @CrossOrigin
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        final Optional<AppUser> optionalAppUser = this.appUserRepository.findById(id);
        if (optionalAppUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        AppUser appUser = optionalAppUser.get();
        if (appUser.getRole() == Role.ADMIN && this.appUserRepository.countAppUserByRole(Role.ADMIN) <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        this.tokenRepository.deleteAllByUserId(id);
        this.appUserRepository.delete(appUser);
        return ResponseEntity.ok(null);

    }
}

