package com.derick.autenticacion;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class KeycloakRestTemplateFactory {
    @Value("${keycloak.auth-server-url}")
    private String keyCloakAuthServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.resource}")
    private String resourceClientId;

    @Value("${keycloak.credentials.secret}")
    private String resourceClientSecret;

    /**
     * Obtener un token para un usuario y contraseña concreto
     * @param username Usuario en keycloak
     * @param password Constraseña del usuario en keycloak
     * @return Objeto con los datos del token
     */

    public TokenManager getTokenManager(String username, String password) {
        Keycloak keycloak = Keycloak.getInstance(keyCloakAuthServerUrl,
                keycloakRealm,
                username,
                password,
                resourceClientId,
                resourceClientSecret);

        TokenManager tokenmanager = keycloak.tokenManager();

        return tokenmanager;
    }

    public RestTemplate oauth2RestTemplate(TokenManager tokenmanager) {
        ClientHttpRequestInterceptor clientHttpRequestInterceptor = (request, body, execution) -> {
            AccessTokenResponse accessToken1 = tokenmanager.getAccessToken(); //only refreshes token when necessary
            String accessToken = accessToken1.getToken(); //get token as String
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        };

        RestTemplate template = new RestTemplate();
        template.setInterceptors(List.of(clientHttpRequestInterceptor));
        return template;
    }
}