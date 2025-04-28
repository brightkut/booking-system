package com.brightkut.hotelservice.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client")
public class ClientProperties {
    private String userClientUrl;

    public String getUserClientUrl() {
        return userClientUrl;
    }

    public void setUserClientUrl(String userClientUrl) {
        this.userClientUrl = userClientUrl;
    }
}
