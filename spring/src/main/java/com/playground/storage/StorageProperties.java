package com.playground.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    @Value("${playground.img.playground}")
    private String locationPlayground;

    @Value("${playground.img.user}")
    private String locationUser;

    public String getLocationPlayground() {
        return locationPlayground;
    }

    public String getLocationUser() {
        return locationUser;
    }

    public void setLocationPlayground(String locationPlayground) {
        this.locationPlayground = locationPlayground;
    }

    public void setLocationUser(String locationUser) {
        this.locationUser = locationUser;
    }

}
