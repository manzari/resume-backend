package me.manzari.resume.service;

import me.manzari.resume.model.AppUser;
import me.manzari.resume.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ActionService {

    private static final Logger LOG = Logger.getAnonymousLogger();
    private final NotificationService notificationService;
    private final TrackingService trackingService;
    private final AppUserRepository appUserRepository;

    public ActionService(NotificationService notificationService, TrackingService trackingService, AppUserRepository appUserRepository) {
        this.notificationService = notificationService;
        this.trackingService = trackingService;
        this.appUserRepository = appUserRepository;
    }

    public void process(Action action, String url, Object principal) {
        String userName = null;
        if (principal instanceof AppUser appUser) {
            userName = appUser.getName();
            if (userName == null) {
                Optional<AppUser> user = this.appUserRepository.findByUsername(appUser.getUsername());
                userName = user.map(AppUser::getName).orElse(null);
            }
        }
        try {
            if (action == Action.Login) {
                this.notificationService.notify(String.format("User '%s' logged in", userName));
            }
            this.trackingService.track(action, url, userName);
        } catch (InterruptedException e) {
            LOG.warning("Interrupted while processing action: " + action.getName());
        }
    }
}
