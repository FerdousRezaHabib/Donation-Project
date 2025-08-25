package org.example.donation_project;

import java.util.Optional;

public final class AppConfig {
    public static final String DB_HOST = getenvOr("DB_HOST", "localhost");
    public static final String DB_PORT = getenvOr("DB_PORT", "3306");
    public static final String DB_NAME = getenvOr("DB_NAME", "donation_mvp");
    public static final String DB_USER = getenvOr("DB_USER", "root");
    public static final String DB_PASS = getenvOr("DB_PASS", "1234"); // <-- fixed

    public static final String JDBC_URL = String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8",
            DB_HOST, DB_PORT, DB_NAME
    );

    public static final String ADMIN_EMAIL    = getenvOr("ADMIN_EMAIL", "mod@example.com");
    public static final String ADMIN_PASSWORD = getenvOr("ADMIN_PASSWORD", "ChangeMe!123");
    public static final int    BCRYPT_ROUNDS  = parseIntOr("BCRYPT_ROUNDS", 10);

    private AppConfig() {}

    private static String getenvOr(String key, String fallback) {
        return Optional.ofNullable(System.getenv(key))
                .filter(s -> !s.isBlank())
                .orElse(fallback);
    }

    private static int parseIntOr(String key, int fallback) {
        try {
            String v = System.getenv(key);
            return (v == null || v.isBlank()) ? fallback : Integer.parseInt(v);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
