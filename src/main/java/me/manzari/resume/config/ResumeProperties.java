package me.manzari.resume.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("resume")
public class ResumeProperties {
    private String frontendUrl;
    private String dataPath;
    private String defaultAdminPassword;
    private String defaultUserPassword;
    private String defaultUserName;
    private Boolean matomoEnabled;
    private String matomoUrl;
    private String matomoSiteId;
    private Boolean notificationsEnabled;
    private String gotifyUrl;
    private String gotifyToken;
    private Integer notificationPriority;

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDefaultAdminPassword() {
        return defaultAdminPassword;
    }

    public void setDefaultAdminPassword(String defaultAdminPassword) {
        this.defaultAdminPassword = defaultAdminPassword;
    }

    public String getDefaultUserPassword() {
        return defaultUserPassword;
    }

    public void setDefaultUserPassword(String defaultUserPassword) {
        this.defaultUserPassword = defaultUserPassword;
    }

    public String getDefaultUserName() {
        return defaultUserName;
    }

    public void setDefaultUserName(String defaultUserName) {
        this.defaultUserName = defaultUserName;
    }

    public String getMatomoSiteId() {
        return matomoSiteId;
    }

    public void setMatomoSiteId(String matomoSiteId) {
        this.matomoSiteId = matomoSiteId;
    }

    public Boolean getMatomoEnabled() {
        return matomoEnabled;
    }

    public void setMatomoEnabled(Boolean matomoEnabled) {
        this.matomoEnabled = matomoEnabled;
    }

    public String getMatomoUrl() {
        return matomoUrl;
    }

    public void setMatomoUrl(String matomoUrl) {
        this.matomoUrl = matomoUrl;
    }

    public String getFrontendUrl() {
        return frontendUrl;
    }

    public void setFrontendUrl(String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getGotifyUrl() {
        return gotifyUrl;
    }

    public void setGotifyUrl(String gotifyUrl) {
        this.gotifyUrl = gotifyUrl;
    }

    public String getGotifyToken() {
        return gotifyToken;
    }

    public void setGotifyToken(String gotifyToken) {
        this.gotifyToken = gotifyToken;
    }

    public Integer getNotificationPriority() {
        return notificationPriority;
    }

    public void setNotificationPriority(Integer notificationPriority) {
        this.notificationPriority = notificationPriority;
    }
}
