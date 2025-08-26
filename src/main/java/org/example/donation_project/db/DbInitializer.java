//package org.example.donation_project.db;
//
//import org.example.donation_project.AppConfig;
//
//import java.sql.*;
//import java.time.Instant;
//
//public final class DBInitializer {
//
//    private DBInitializer() {}
//
//    public static void initialize() throws SQLException {
//        try (Connection conn = Db.getConnection()) {
//            conn.setAutoCommit(false);
//            try {
//                createUsersTable(conn);
//                createStudentProfileTable(conn);
//                createDonationsTable(conn);
//                createNotificationsTable(conn);
//                createModerationActionsTable(conn);
//                seedModeratorIfMissing(conn);
//                conn.commit();
//            } catch (SQLException ex) {
//                conn.rollback();
//                throw ex;
//            } finally {
//                conn.setAutoCommit(true);
//            }
//        }
//    }
//
//    private static void createUsersTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS users (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              email VARCHAR(191) NOT NULL UNIQUE,
//              password_hash VARCHAR(100) NOT NULL,
//              role ENUM('student','donor','moderator') NOT NULL,
//              status ENUM('pending','active','rejected','blocked') NOT NULL,
//              created_at DATETIME NOT NULL,
//              updated_at DATETIME NOT NULL,
//              INDEX idx_users_role_status (role, status)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        exec(conn, sql);
//    }
//
//    private static void createStudentProfileTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS student_profile (
//              user_id BIGINT PRIMARY KEY,
//              name VARCHAR(120) NOT NULL,
//              school VARCHAR(160) NOT NULL,
//              class_year SMALLINT NOT NULL,
//              bio VARCHAR(500) NULL,
//              photo_url VARCHAR(255) NULL,
//              created_at DATETIME NOT NULL,
//              updated_at DATETIME NOT NULL,
//              CONSTRAINT fk_sp_user FOREIGN KEY (user_id)
//                REFERENCES users(id) ON DELETE CASCADE
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        exec(conn, sql);
//    }
//
//    private static void createDonationsTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS donations (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              donation_uuid CHAR(36) NOT NULL UNIQUE,
//              donor_id BIGINT NOT NULL,
//              student_id BIGINT NOT NULL,
//              amount DECIMAL(10,2) NOT NULL,
//              created_at DATETIME NOT NULL,
//              INDEX idx_donations_student (student_id, created_at),
//              INDEX idx_donations_donor (donor_id, created_at),
//              CONSTRAINT fk_donations_donor FOREIGN KEY (donor_id) REFERENCES users(id),
//              CONSTRAINT fk_donations_student FOREIGN KEY (student_id) REFERENCES users(id),
//              CONSTRAINT chk_amount CHECK (amount > 0)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        exec(conn, sql);
//    }
//
//    private static void createNotificationsTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS notifications (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              user_id BIGINT NOT NULL,
//              type ENUM('donation_received','system','moderation') NOT NULL,
//              title VARCHAR(120) NOT NULL,
//              message VARCHAR(500) NOT NULL,
//              is_read TINYINT(1) NOT NULL DEFAULT 0,
//              created_at DATETIME NOT NULL,
//              INDEX idx_notifications_user_unread (user_id, is_read, created_at),
//              CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        exec(conn, sql);
//    }
//
//    private static void createModerationActionsTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS moderation_actions (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              moderator_id BIGINT NOT NULL,
//              target_user_id BIGINT NOT NULL,
//              action ENUM('approve_student','reject_student','delete_student','block_donor','unblock_donor') NOT NULL,
//              reason VARCHAR(500) NULL,
//              created_at DATETIME NOT NULL,
//              INDEX idx_mod_actions_target_time (target_user_id, created_at),
//              CONSTRAINT fk_mod_actions_moderator FOREIGN KEY (moderator_id) REFERENCES users(id),
//              CONSTRAINT fk_mod_actions_target FOREIGN KEY (target_user_id) REFERENCES users(id)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        exec(conn, sql);
//    }
//
//    private static void seedModeratorIfMissing(Connection conn) throws SQLException {
//        final String checkSql = "SELECT id FROM users WHERE role='moderator' LIMIT 1";
//        try (PreparedStatement ps = conn.prepareStatement(checkSql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) return;
//        }
//
//        final String insertSql = """
//            INSERT INTO users (email, password_hash, role, status, created_at, updated_at)
//            VALUES (?, ?, 'moderator', 'active', ?, ?)
//        """;
//        String now = tsNow();
//        String hash = bcrypt(AppConfig.ADMIN_PASSWORD, AppConfig.BCRYPT_ROUNDS);
//
//        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
//            ps.setString(1, AppConfig.ADMIN_EMAIL);
//            ps.setString(2, hash);
//            ps.setString(3, now);
//            ps.setString(4, now);
//            ps.executeUpdate();
//        }
//        System.out.println("[DBInitializer] Seeded moderator account: " + AppConfig.ADMIN_EMAIL);
//    }
//
//    private static void exec(Connection conn, String ddl) throws SQLException {
//        try (Statement st = conn.createStatement()) {
//            st.execute(ddl);
//        }
//    }
//
//    private static String tsNow() {
//        return Timestamp.from(Instant.now()).toString().substring(0, 19).replace('T', ' ');
//    }
//
//    private static String bcrypt(String password, int rounds) {
//        try {
//            return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt(rounds));
//        } catch (NoClassDefFoundError e) {
//            throw new RuntimeException("""
//                jBCrypt library is missing. Add dependency:
//                Maven: <dependency><groupId>org.mindrot</groupId><artifactId>jbcrypt</artifactId><version>0.4</version></dependency>
//                Gradle: implementation 'org.mindrot:jbcrypt:0.4'
//                """, e);
//        }
//    }
//}


//package org.example.donation_project.db;
//
//import org.example.donation_project.AppConfig;
//
//import java.sql.*;
//import java.time.Instant;
//
//public final class DBInitializer {
//
//    private DBInitializer() {}
//
//    public static void initialize() throws SQLException {
//        System.out.println("[DBInitializer] Starting database initialization...");
//        try (Connection conn = Db.getConnection()) {
//            conn.setAutoCommit(false);
//            try {
//                createUsersTable(conn);
//                createStudentProfileTable(conn);
//                createDonationsTable(conn);
//                createNotificationsTable(conn);
//                createModerationActionsTable(conn);
//                seedModeratorIfMissing(conn);
//                conn.commit();
//                System.out.println("[DBInitializer] Database initialized successfully.");
//            } catch (SQLException ex) {
//                conn.rollback();
//                System.err.println("[DBInitializer] Error during database initialization: " + ex.getMessage());
//                throw ex;
//            } finally {
//                conn.setAutoCommit(true);
//            }
//        }
//    }
//
//    private static void createUsersTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS users (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              email VARCHAR(191) NOT NULL UNIQUE,
//              password_hash VARCHAR(100) NOT NULL,
//              role ENUM('student','donor','moderator') NOT NULL,
//              status ENUM('pending','active','rejected','blocked') NOT NULL,
//              created_at DATETIME NOT NULL,
//              updated_at DATETIME NOT NULL,
//              INDEX idx_users_role_status (role, status)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        System.out.println("[DBInitializer] Creating users table...");
//        exec(conn, sql);
//        System.out.println("[DBInitializer] Users table created (or already exists).");
//    }
//
//    private static void createStudentProfileTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS student_profile (
//              user_id BIGINT PRIMARY KEY,
//              name VARCHAR(120) NOT NULL,
//              school VARCHAR(160) NOT NULL,
//              class_year SMALLINT NOT NULL,
//              bio VARCHAR(500) NULL,
//              photo_url VARCHAR(255) NULL,
//              created_at DATETIME NOT NULL,
//              updated_at DATETIME NOT NULL,
//              CONSTRAINT fk_sp_user FOREIGN KEY (user_id)
//                REFERENCES users(id) ON DELETE CASCADE
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        System.out.println("[DBInitializer] Creating student_profile table...");
//        exec(conn, sql);
//        System.out.println("[DBInitializer] student_profile table created (or already exists).");
//    }
//
//    private static void createDonationsTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS donations (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              donation_uuid CHAR(36) NOT NULL UNIQUE,
//              donor_id BIGINT NOT NULL,
//              student_id BIGINT NOT NULL,
//              amount DECIMAL(10,2) NOT NULL,
//              created_at DATETIME NOT NULL,
//              INDEX idx_donations_student (student_id, created_at),
//              INDEX idx_donations_donor (donor_id, created_at),
//              CONSTRAINT fk_donations_donor FOREIGN KEY (donor_id) REFERENCES users(id),
//              CONSTRAINT fk_donations_student FOREIGN KEY (student_id) REFERENCES users(id),
//              CONSTRAINT chk_amount CHECK (amount > 0)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        System.out.println("[DBInitializer] Creating donations table...");
//        exec(conn, sql);
//        System.out.println("[DBInitializer] Donations table created (or already exists).");
//    }
//
//    private static void createNotificationsTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS notifications (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              user_id BIGINT NOT NULL,
//              type ENUM('donation_received','system','moderation') NOT NULL,
//              title VARCHAR(120) NOT NULL,
//              message VARCHAR(500) NOT NULL,
//              is_read TINYINT(1) NOT NULL DEFAULT 0,
//              created_at DATETIME NOT NULL,
//              INDEX idx_notifications_user_unread (user_id, is_read, created_at),
//              CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        System.out.println("[DBInitializer] Creating notifications table...");
//        exec(conn, sql);
//        System.out.println("[DBInitializer] Notifications table created (or already exists).");
//    }
//
//    private static void createModerationActionsTable(Connection conn) throws SQLException {
//        String sql = """
//            CREATE TABLE IF NOT EXISTS moderation_actions (
//              id BIGINT AUTO_INCREMENT PRIMARY KEY,
//              moderator_id BIGINT NOT NULL,
//              target_user_id BIGINT NOT NULL,
//              action ENUM('approve_student','reject_student','delete_student','block_donor','unblock_donor') NOT NULL,
//              reason VARCHAR(500) NULL,
//              created_at DATETIME NOT NULL,
//              INDEX idx_mod_actions_target_time (target_user_id, created_at),
//              CONSTRAINT fk_mod_actions_moderator FOREIGN KEY (moderator_id) REFERENCES users(id),
//              CONSTRAINT fk_mod_actions_target FOREIGN KEY (target_user_id) REFERENCES users(id)
//            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
//        """;
//        System.out.println("[DBInitializer] Creating moderation_actions table...");
//        exec(conn, sql);
//        System.out.println("[DBInitializer] Moderation actions table created (or already exists).");
//    }
//
//    private static void seedModeratorIfMissing(Connection conn) throws SQLException {
//        final String checkSql = "SELECT id FROM users WHERE role='moderator' LIMIT 1";
//        try (PreparedStatement ps = conn.prepareStatement(checkSql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) return;
//        }
//
//        final String insertSql = """
//            INSERT INTO users (email, password_hash, role, status, created_at, updated_at)
//            VALUES (?, ?, 'moderator', 'active', ?, ?)
//        """;
//        String now = tsNow();
//        String hash = bcrypt(AppConfig.ADMIN_PASSWORD, AppConfig.BCRYPT_ROUNDS);
//
//        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
//            ps.setString(1, AppConfig.ADMIN_EMAIL);
//            ps.setString(2, hash);
//            ps.setString(3, now);
//            ps.setString(4, now);
//            ps.executeUpdate();
//        }
//        System.out.println("[DBInitializer] Seeded moderator account: " + AppConfig.ADMIN_EMAIL);
//    }
//
//    private static void exec(Connection conn, String ddl) throws SQLException {
//        try (Statement st = conn.createStatement()) {
//            System.out.println("Executing SQL: " + ddl); // Debugging: print the query being executed
//            st.execute(ddl);
//        }
//    }
//
//    private static String tsNow() {
//        return Timestamp.from(Instant.now()).toString().substring(0, 19).replace('T', ' ');
//    }
//
//    private static String bcrypt(String password, int rounds) {
//        try {
//            return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt(rounds));
//        } catch (NoClassDefFoundError e) {
//            throw new RuntimeException("""
//                jBCrypt library is missing. Add dependency:
//                Maven: <dependency><groupId>org.mindrot</groupId><artifactId>jbcrypt</artifactId><version>0.4</version></dependency>
//                Gradle: implementation 'org.mindrot:jbcrypt:0.4'
//                """, e);
//        }
//    }
//}

package org.example.donation_project.db;

import org.example.donation_project.AppConfig;

import java.sql.*;
import java.time.Instant;

public final class DbInitializer {

    private DbInitializer() {}

    public static void initialize() throws SQLException {
        try (Connection conn = Db.getConnection()) {
            // Check if the "users" table already exists before running initialization
            if (!isTableExists(conn, "users")) {
                // Run table creation if they don't exist
                conn.setAutoCommit(false);
                try {
                    // Creating tables
                    createUsersTable(conn);
                    createStudentProfileTable(conn);
                    createDonationsTable(conn);
                    createNotificationsTable(conn);
                    createModerationActionsTable(conn);
                    seedModeratorIfMissing(conn);
                    conn.commit();
                    System.out.println("Database tables created successfully.");
                } catch (SQLException ex) {
                    conn.rollback();
                    throw ex;
                } finally {
                    conn.setAutoCommit(true);
                }
            } else {
                System.out.println("Tables already exist, skipping initialization.");
            }
        }
    }

    // Check if a table exists in the database
    private static boolean isTableExists(Connection conn, String tableName) throws SQLException {
        String query = "SHOW TABLES LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Returns true if table exists
            }
        }
    }

    // Create the 'users' table if it does not exist
    private static void createUsersTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
              id BIGINT AUTO_INCREMENT PRIMARY KEY,
              email VARCHAR(191) NOT NULL UNIQUE,
              password_hash VARCHAR(100) NOT NULL,
              role ENUM('student', 'donor', 'moderator') NOT NULL,
              status ENUM('pending', 'active', 'rejected', 'blocked') NOT NULL,
              created_at DATETIME NOT NULL,
              updated_at DATETIME NOT NULL,
              INDEX idx_users_role_status (role, status)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """;
        exec(conn, sql);
    }

    // Create the 'student_profile' table if it does not exist
    private static void createStudentProfileTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS student_profile (
              user_id BIGINT PRIMARY KEY,
              name VARCHAR(120) NOT NULL,
              school VARCHAR(160) NOT NULL,
              class_year SMALLINT NOT NULL,
              bio VARCHAR(500) NULL,
              photo_url VARCHAR(255) NULL,
              created_at DATETIME NOT NULL,
              updated_at DATETIME NOT NULL,
              CONSTRAINT fk_sp_user FOREIGN KEY (user_id)
                REFERENCES users(id) ON DELETE CASCADE
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """;
        exec(conn, sql);
    }

    // Create the 'donations' table if it does not exist
    private static void createDonationsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS donations (
              id BIGINT AUTO_INCREMENT PRIMARY KEY,
              donation_uuid CHAR(36) NOT NULL UNIQUE,
              donor_id BIGINT NOT NULL,
              student_id BIGINT NOT NULL,
              amount DECIMAL(10,2) NOT NULL,
              created_at DATETIME NOT NULL,
              INDEX idx_donations_student (student_id, created_at),
              INDEX idx_donations_donor (donor_id, created_at),
              CONSTRAINT fk_donations_donor FOREIGN KEY (donor_id) REFERENCES users(id),
              CONSTRAINT fk_donations_student FOREIGN KEY (student_id) REFERENCES users(id),
              CONSTRAINT chk_amount CHECK (amount > 0)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """;
        exec(conn, sql);
    }

    // Create the 'notifications' table if it does not exist
    private static void createNotificationsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS notifications (
              id BIGINT AUTO_INCREMENT PRIMARY KEY,
              user_id BIGINT NOT NULL,
              type ENUM('donation_received','system','moderation') NOT NULL,
              title VARCHAR(120) NOT NULL,
              message VARCHAR(500) NOT NULL,
              is_read TINYINT(1) NOT NULL DEFAULT 0,
              created_at DATETIME NOT NULL,
              INDEX idx_notifications_user_unread (user_id, is_read, created_at),
              CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """;
        exec(conn, sql);
    }

    // Create the 'moderation_actions' table if it does not exist
    private static void createModerationActionsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS moderation_actions (
              id BIGINT AUTO_INCREMENT PRIMARY KEY,
              moderator_id BIGINT NOT NULL,
              target_user_id BIGINT NOT NULL,
              action ENUM('approve_student','reject_student','delete_student','block_donor','unblock_donor') NOT NULL,
              reason VARCHAR(500) NULL,
              created_at DATETIME NOT NULL,
              INDEX idx_mod_actions_target_time (target_user_id, created_at),
              CONSTRAINT fk_mod_actions_moderator FOREIGN KEY (moderator_id) REFERENCES users(id),
              CONSTRAINT fk_mod_actions_target FOREIGN KEY (target_user_id) REFERENCES users(id)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """;
        exec(conn, sql);
    }

    // Seed a moderator if one doesn't exist
    private static void seedModeratorIfMissing(Connection conn) throws SQLException {
        final String checkSql = "SELECT id FROM users WHERE role='moderator' LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(checkSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return;  // Moderator already exists
        }

        final String insertSql = """
            INSERT INTO users (email, password_hash, role, status, created_at, updated_at)
            VALUES (?, ?, 'moderator', 'active', ?, ?)
        """;
        String now = tsNow();
        String hash = bcrypt(AppConfig.ADMIN_PASSWORD, AppConfig.BCRYPT_ROUNDS);

        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, AppConfig.ADMIN_EMAIL);
            ps.setString(2, hash);
            ps.setString(3, now);
            ps.setString(4, now);
            ps.executeUpdate();
        }
        System.out.println("[DbInitializer] Seeded moderator account: " + AppConfig.ADMIN_EMAIL);
    }

    // Execute SQL command
    private static void exec(Connection conn, String ddl) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute(ddl);
        }
    }

    // Get current timestamp in a format suitable for MySQL
    private static String tsNow() {
        return Timestamp.from(Instant.now()).toString().substring(0, 19).replace('T', ' ');
    }

    // BCrypt hashing function for passwords
    private static String bcrypt(String password, int rounds) {
        try {
            return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt(rounds));
        } catch (NoClassDefFoundError e) {
            throw new RuntimeException("""
                jBCrypt library is missing. Add dependency:
                Maven: <dependency><groupId>org.mindrot</groupId><artifactId>jbcrypt</artifactId><version>0.4</version></dependency>
                Gradle: implementation 'org.mindrot:jbcrypt:0.4'
                """, e);
        }
    }
}
