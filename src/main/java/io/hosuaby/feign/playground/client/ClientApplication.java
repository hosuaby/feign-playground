package io.hosuaby.feign.playground.client;

import feign.Feign;
import feign.Logger;
import feign.auth.oauth2.KeycloakAuthentication;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import io.hosuaby.feign.playground.Exchange;
import io.hosuaby.feign.playground.domain.Mixin;
import io.hosuaby.feign.playground.keys.KeycloakCredentials;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ClientApplication {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);

    public static void main(String[] args) throws InterruptedException {
        int keycloakPort = Exchange.getKeycloakPort();

        KeycloakAuthentication keycloakAuthentication =
                KeycloakAuthentication.withClientSecretBasic(
                        String.format("http://localhost:%d", keycloakPort),
                        KeycloakCredentials.REALM,
                        KeycloakCredentials.FEIGN_CLIENT_ID_WITH_SECRET,
                        KeycloakCredentials.CLIENT_SECRET_CREDENTIALS);

        IcecreamClient client = Feign
                .builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .addCapability(keycloakAuthentication)
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(IcecreamClient.class, "http://localhost:8080");

        while (true) {
            final Collection<Mixin> mixins = client.getAvailableMixins();
            LOGGER.info("Fetched mixins: {}", mixins);

            Thread.sleep(5 * 1000);
        }
    }
}
