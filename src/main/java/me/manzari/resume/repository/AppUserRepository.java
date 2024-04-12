package me.manzari.resume.repository;


import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByRole(Role role);
    int countAppUserByRole(Role role);
}
