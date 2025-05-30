package com.brightkut.hotelservice.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.brightkut.kei.api.ApiRes;
import com.brightkut.kei.exception.UnAuthorizeException;

@Service
public class UserClient {
    private final RestClient restClient;

    public UserClient(ClientProperties clientProperties) {
        this.restClient = RestClient.builder().baseUrl(clientProperties.getUserClientUrl()).build();
    }

    public void verifyAccessToken(String accessToken) {
        try {
            restClient
                    .post()
                    .uri("/verify-access-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .retrieve()
                    .body(ApiRes.class);
        } catch (Exception e) {
            throw new UnAuthorizeException("Error when verify accessToken");
        }
    }
}
