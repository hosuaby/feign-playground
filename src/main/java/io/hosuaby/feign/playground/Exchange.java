package io.hosuaby.feign.playground;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Exchange {
    private static final Path TMP_FILE = Path
            .of(System.getProperty("java.io.tmpdir"))
            .resolve("feign-playground.tmp");

    public static void setKeycloakPort(int port) {
        try {
            Files.writeString(TMP_FILE, String.valueOf(port));
        } catch (IOException writeFileException) {
            throw new RuntimeException(writeFileException);
        }
    }

    public static int getKeycloakPort() {
        try {
            String content = Files.readString(TMP_FILE);
            return Integer.parseInt(content);
        } catch (IOException readFileException) {
            throw new RuntimeException(readFileException);
        }
    }
}
