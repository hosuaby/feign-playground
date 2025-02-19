package io.hosuaby.feign.playground.server;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.hosuaby.feign.playground.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Collections;

@Configuration
public class KeycloakPropertySourceLocator implements PropertySourceLocator {

    @Autowired
    KeycloakContainer keycloakContainer;

    @Override
    public PropertySource<?> locate(final Environment environment) {
        int keycloakPort = keycloakContainer.getHttpPort();
        Exchange.setKeycloakPort(keycloakPort);
        return new MapPropertySource("testcontainers", Collections.singletonMap("keycloak_port", keycloakPort));
    }
}
