package me.manzari.resume;

import me.manzari.resume.config.ResumeProperties;
import me.manzari.resume.model.AppUser;
import me.manzari.resume.model.Role;
import me.manzari.resume.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class FirstRunHelper {

    private static final Logger LOG
            = Logger.getAnonymousLogger();

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResumeProperties resumeProperties;

    public FirstRunHelper(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, ResumeProperties resumeProperties) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.resumeProperties = resumeProperties;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<AppUser> result = this.appUserRepository.findByRole(Role.ADMIN);
        if (result.isEmpty()) {
            LOG.info("There is no admin user in the system");
            AppUser newUser = new AppUser();
            newUser.setUsername("adm1n");
            newUser.setPassword(passwordEncoder.encode(resumeProperties.getDefaultPassword()));
            newUser.setRole(Role.ADMIN);

            appUserRepository.save(newUser);
            LOG.info("Created user adm1n with default password from properties");
        }
    }
}