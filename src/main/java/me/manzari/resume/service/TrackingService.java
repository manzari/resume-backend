package me.manzari.resume.service;

import me.manzari.resume.config.ResumeProperties;
import me.manzari.resume.model.AppUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;
import java.util.logging.Logger;


@Service
public class TrackingService {

    private final ResumeProperties resumeProperties;
    private static final Logger LOG = Logger.getAnonymousLogger();


    public TrackingService(ResumeProperties resumeProperties) {
        this.resumeProperties = resumeProperties;
    }

    @Async
    public void track(String action, String url, Object principal) throws InterruptedException {
        if (!resumeProperties.getMatomoEnabled()) {
            return;
        }
        String uid = null;
        if (principal instanceof AppUser appUser) {
            uid = appUser.getName();
        }
        LOG.info(String.format("Tracking action '%s' at '%s' was done by '%s'", action, url, uid));
        Random random = new Random();
        int randomInt = random.nextInt(100000);
        UriComponentsBuilder matomoUrl = UriComponentsBuilder.fromHttpUrl(resumeProperties.getMatomoUrl() + "/matomo.php")
                .queryParam("action_name", action)
                .queryParam("url", resumeProperties.getFrontendUrl() + url)
                .queryParam("idsite", resumeProperties.getMatomoSiteId())
                .queryParam("rand", randomInt)
                .queryParam("rec", 1);

        if (uid != null) {
            matomoUrl.queryParam("uid", uid);
        }

        RestTemplate restTemplate = new RestTemplate();
        LOG.info(String.format("Requesting URL '%s'", matomoUrl.toUriString()));
        restTemplate.getForEntity(matomoUrl.toUriString(), String.class);
    }
}
