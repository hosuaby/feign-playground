package io.hosuaby.feign.playground.server;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.hosuaby.feign.playground.keys.KeyUtils;
import io.hosuaby.inject.resources.spring.EnableResourceInjection;
import io.hosuaby.inject.resources.spring.TextResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.hosuaby.feign.playground.keys.KeycloakCredentials;

import java.util.Collections;

@Configuration
@EnableResourceInjection
public class KeycloakConfig {

    @TextResource("certificate.pem")
    static String certificatePem;

    @Bean(destroyMethod = "stop")
    public KeycloakContainer keycloakContainer() {
        KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:26.1.0");
        keycloak.start();
        configureKeycloak(keycloak);
        return keycloak;
    }
    
    static void configureKeycloak(KeycloakContainer keycloak) {
        ClientRepresentation clientFeignSecret = new ClientRepresentation();
        clientFeignSecret.setClientId(KeycloakCredentials.FEIGN_CLIENT_ID_WITH_SECRET);
        clientFeignSecret.setSecret(KeycloakCredentials.CLIENT_SECRET_CREDENTIALS);
        clientFeignSecret.setServiceAccountsEnabled(true);
        clientFeignSecret.setClientAuthenticatorType("client-secret");

        ClientRepresentation clientJwtSignedWithSecret = new ClientRepresentation();
        clientJwtSignedWithSecret.setClientId(KeycloakCredentials.FEIGN_CLIENT_ID_JWT_SIGNED_WITH_SECRET);
        clientJwtSignedWithSecret.setSecret(KeycloakCredentials.CLIENT_SECRET_SIGNATURE);
        clientJwtSignedWithSecret.setServiceAccountsEnabled(true);
        clientJwtSignedWithSecret.setClientAuthenticatorType("client-secret-jwt");

        ClientRepresentation clientPrivateKeyJwt = new ClientRepresentation();
        clientPrivateKeyJwt.setClientId(KeycloakCredentials.FEIGN_CLIENT_ID_PRIVATE_KEY_JWT);
        clientPrivateKeyJwt.setServiceAccountsEnabled(true);
        clientPrivateKeyJwt.setClientAuthenticatorType("client-jwt");

        String certificate = KeyUtils.parseKey(certificatePem);
        clientPrivateKeyJwt.setAttributes(Collections.singletonMap(
                "jwt.credential.certificate", certificate
        ));

        RealmResource masterRealm = keycloak
                .getKeycloakAdminClient()
                .realms()
                .realm(KeycloakContainer.MASTER_REALM);

        ClientsResource clientsApi = masterRealm.clients();
        clientsApi.create(clientFeignSecret);
        clientsApi.create(clientJwtSignedWithSecret);
        clientsApi.create(clientPrivateKeyJwt);

        RealmRepresentation representation = masterRealm.toRepresentation();
        representation.setAccessTokenLifespan(10);
        masterRealm.update(representation);
    }
}
