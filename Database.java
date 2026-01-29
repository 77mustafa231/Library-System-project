package com.example.library.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.stream.Collectors;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.home") + "/library.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        try (Connection conn = connect()) {
            // Load schema.sql from resources
            InputStream in = Database.class.getClassLoader().getResourceAsStream("schema.sql");
            if (in == null) {
                throw new RuntimeException("schema.sql not found in resources");
            }
            String sql = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            try (Statement stmt = conn.createStatement()) {
                for (String part : sql.split(";")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB init failed", e);
        }
    }
}
