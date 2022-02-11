package com.derick.autenticacion;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
public class Test {
    @Autowired
    KeycloakRestTemplateFactory keycloakRestTemplateFactory;

    Test() {
        TokenManager tokenManager = keycloakRestTemplateFactory.getTokenManager("e000401", "12345");

        log.info("getTokenManager.getAccessToken: " + tokenManager.getAccessToken());

        RestTemplate rt = keycloakRestTemplateFactory.oauth2RestTemplate(tokenManager);

        // rt.put("http://localhost:8081/auth/realms/Opplus/protocol/openid-connect/userinfo", null);


//
//        HttpEntity<String> entity = new HttpEntity<>("body", headers);
//
//        rt.postForObject(url, entity, String.class);
//
        ResponseEntity<String> response = rt.getForEntity("http://localhost:8081/auth/realms/Opplus/protocol/openid-connect/userinfo", String.class);
//        String body = response.getBody();
//
//        log.info("body: " + body);

        // assertEquals("windig", body);
    }
}