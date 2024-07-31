package me.manzari.resume.service;

import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.AuthenticationResponse;
import me.manzari.resume.model.Token;
import me.manzari.resume.repository.AppUserRepository;
import me.manzari.resume.repository.TokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AppUserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(AppUser requestedUser) {

        if (repository.findByUsername(requestedUser.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exists");
        }

        AppUser newUser = new AppUser();
        newUser.setName(requestedUser.getName());
        newUser.setUsername(requestedUser.getUsername());
        newUser.setPassword(passwordEncoder.encode(requestedUser.getPassword().toUpperCase()));
        newUser.setRole(requestedUser.getRole());

        newUser = repository.save(newUser);

        String jwt = jwtService.generateToken(newUser);

        saveUserToken(jwt, newUser);

        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    public AuthenticationResponse authenticate(AppUser request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Optional<AppUser> user = repository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            return new AuthenticationResponse(null, "User not found.");
        }
        String jwt = jwtService.generateToken(user.get());

        revokeAllTokenByUser(user.get());
        saveUserToken(jwt, user.get());
        return new AuthenticationResponse(jwt, "User login was successful");

    }

    private void revokeAllTokenByUser(AppUser user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> t.setLoggedOut(true));

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, AppUser user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}