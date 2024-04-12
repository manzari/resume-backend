package me.manzari.resume.service;

import me.manzari.resume.config.ResumeProperties;
import me.manzari.resume.model.NotificationMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;


@Service
public class NotificationService {

    private static final Logger LOG = Logger.getAnonymousLogger();
    private final ResumeProperties resumeProperties;


    public NotificationService(ResumeProperties resumeProperties) {
        this.resumeProperties = resumeProperties;
    }

    @Async
    public void notify(String message) throws InterruptedException {
        notify(message, "Resume");
    }

    @Async
    public void notify(String message, String title) {
        if (!resumeProperties.getNotificationsEnabled()) {
            return;
        }
        LOG.info(String.format("Sending Notification '%s' - '%s'", title, message));
        String token = resumeProperties.getGotifyToken();
        String url = resumeProperties.getGotifyUrl();
        Integer priority = resumeProperties.getNotificationPriority();

        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setPriority(priority);
        notificationMessage.setTitle(title);
        notificationMessage.setMessage(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Gotify-Key", token);
        HttpEntity<NotificationMessage> request = new HttpEntity<>(notificationMessage, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
        if (!result.getStatusCode().is2xxSuccessful()) {
            LOG.warning(String.format(
                    "Error sending Notification '%s', Gotify returned: %s %s",
                    notificationMessage,
                    result.getStatusCode().value(),
                    result.getBody()
            ));
        }

    }


}
