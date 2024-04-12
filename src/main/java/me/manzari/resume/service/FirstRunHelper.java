package me.manzari.resume.service;

import me.manzari.resume.config.ResumeProperties;
import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.Resume;
import me.manzari.resume.model.Role;
import me.manzari.resume.repository.AppUserRepository;
import me.manzari.resume.repository.ResumeRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class FirstRunHelper {

    private static final Logger LOG
            = Logger.getAnonymousLogger();

    private final AppUserRepository appUserRepository;
    private final ResumeRepository resumeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResumeProperties resumeProperties;

    public FirstRunHelper(AppUserRepository appUserRepository, ResumeRepository resumeRepository, PasswordEncoder passwordEncoder, ResumeProperties resumeProperties) {
        this.appUserRepository = appUserRepository;
        this.resumeRepository = resumeRepository;
        this.passwordEncoder = passwordEncoder;
        this.resumeProperties = resumeProperties;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<AppUser> result = this.appUserRepository.findByRole(Role.ADMIN);
        if (result.isEmpty()) {
            LOG.info("There is no admin user in the system");
            AppUser newAdminUser = new AppUser();
            newAdminUser.setName("Admin");
            newAdminUser.setUsername("adm1n");
            newAdminUser.setPassword(passwordEncoder.encode(resumeProperties.getDefaultAdminPassword()));
            newAdminUser.setRole(Role.ADMIN);

            appUserRepository.save(newAdminUser);
            LOG.info("Created user Admin (adm1n) with default password from properties");
        }
        List<AppUser> result2 = this.appUserRepository.findByRole(Role.USER);
        if (result2.isEmpty()) {
            LOG.info("There is no user in the system");
            AppUser newUser = new AppUser();
            newUser.setName("DefaultUser");
            String password = passwordEncoder.encode(resumeProperties.getDefaultUserPassword());
            newUser.setPassword(password);
            newUser.setUsername(resumeProperties.getDefaultUserName());
            newUser.setRole(Role.USER);

            appUserRepository.save(newUser);
            LOG.info("Created user DefaultUser (" + newUser.getUsername() + ") with default password from properties");
        }
        Optional<Resume> result3 = this.resumeRepository.findById(1L);
        if (result3.isEmpty()) {
            LOG.info("There is no resume with id 1 in the system");
            Resume resume = new Resume();
            resume.setId(1L);
            resume.setContent("{}");
            resumeRepository.save(resume);
            LOG.info("Created empty resume with id 1");
        }
    }
}