package me.manzari.resume.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("resume")
public class ResumeProperties {
    private String apiUrl;
    private String corsUrl;
    private String dataPath;
    private String defaultAdminPassword;
    private String defaultUserPassword;
    private String defaultUserName;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

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

    public String getCorsUrl() {
        return corsUrl;
    }

    public void setCorsUrl(String corsUrl) {
        this.corsUrl = corsUrl;
    }
}
