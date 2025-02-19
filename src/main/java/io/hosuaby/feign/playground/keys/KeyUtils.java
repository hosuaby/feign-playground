package io.hosuaby.feign.playground.keys;

public final class KeyUtils {
    private KeyUtils() {
    }

    public static String parseKey(String fileContent) {
        String[] lines = fileContent.split("\n");
        boolean started = false;

        String key = "";

        for (String line : lines) {
            if (line.startsWith("-----BEGIN")) {
                started = true;
            } else if (line.startsWith("-----END")) {
                break;
            } else if (started) {
                key += line;
            };
        }

        return key;
    }
}
