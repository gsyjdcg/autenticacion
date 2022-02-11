package com.derick.autenticacion;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(classes = AutenticacionApplication.class)
@ActiveProfiles("test")
public class AutenticacionApplicationTests {
    @Autowired
    KeycloakRestTemplateFactory keycloakRestTemplateFactory;

    @Test
    void getTokenManagerTest() {
        TokenManager getTokenManager = keycloakRestTemplateFactory.getTokenManager("e000401", "12345");

        log.info("getTokenManager.getAccessToken: " + getTokenManager.getAccessTokenString());

        assertTrue(getTokenManager != null && getTokenManager.getAccessTokenString() != null);
    }
}
