package me.manzari.resume.service;

import me.manzari.resume.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ActionService {

    private static final Logger LOG = Logger.getAnonymousLogger();
    private final NotificationService notificationService;
    private final TrackingService trackingService;

    public ActionService(NotificationService notificationService, TrackingService trackingService) {
        this.notificationService = notificationService;
        this.trackingService = trackingService;
    }

    public void process(Action action, String url, Object principal) {
        String user = null;
        if (principal instanceof AppUser appUser) {
            user = appUser.getName();
        }
        try {
            if (action == Action.Login) {
                this.notificationService.notify(String.format("User '%s' logged in", user));
            }
            this.trackingService.track(action, url, user);
        } catch (InterruptedException e) {
            LOG.warning("Interrupted while processing action: " + action.getName());
        }
    }
}
