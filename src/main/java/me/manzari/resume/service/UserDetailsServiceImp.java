package me.manzari.resume.service;

import me.manzari.resume.model.AppUser;
import me.manzari.resume.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = this.appUserRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(username);
    }
}
