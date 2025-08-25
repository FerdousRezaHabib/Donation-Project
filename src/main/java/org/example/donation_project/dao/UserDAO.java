package org.example.donation_project.dao;

import org.example.donation_project.db.Db;
import org.example.donation_project.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private static User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRole(User.Role.valueOf(rs.getString("role")));
        u.setStatus(User.Status.valueOf(rs.getString("status")));
        Timestamp c = rs.getTimestamp("created_at");
        Timestamp m = rs.getTimestamp("updated_at");
        u.setCreatedAt(c != null ? c.toLocalDateTime() : null);
        u.setUpdatedAt(m != null ? m.toLocalDateTime() : null);
        return u;
    }

    private static Timestamp ts(LocalDateTime dt) {
        return dt == null ? null : Timestamp.valueOf(dt);
    }

    /** Creates a user and returns the generated id. Also updates the passed User with id/createdAt/updatedAt. */
    public long createUser(User user) throws SQLException {
        final String sql =
                "INSERT INTO users (email, password_hash, role, status, created_at, updated_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getStatus().name());
            LocalDateTime now = LocalDateTime.now();
            ps.setTimestamp(5, ts(now));
            ps.setTimestamp(6, ts(now));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    user.setId(id);
                    user.setCreatedAt(now);
                    user.setUpdatedAt(now);
                    return id;
                }
                throw new SQLException("No generated key returned for users insert.");
            }
        }
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        final String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    public Optional<User> findById(long id) throws SQLException {
        final String sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    public void updateStatus(long userId, User.Status status) throws SQLException {
        final String sql = "UPDATE users SET status = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setTimestamp(2, ts(LocalDateTime.now()));
            ps.setLong(3, userId);
            ps.executeUpdate();
        }
    }

    public List<User> listByRoleAndStatus(User.Role role, User.Status status, int limit, int offset) throws SQLException {
        final String sql =
                "SELECT * FROM users " +
                        "WHERE role = ? AND status = ? " +
                        "ORDER BY created_at DESC " +
                        "LIMIT ? OFFSET ?";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role.name());
            ps.setString(2, status.name());
            ps.setInt(3, limit);
            ps.setInt(4, offset);

            List<User> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        }
    }

    public void updatePasswordHash(long userId, String newHash) throws SQLException {
        final String sql = "UPDATE users SET password_hash = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setTimestamp(2, ts(LocalDateTime.now()));
            ps.setLong(3, userId);
            ps.executeUpdate();
        }
    }

    public boolean emailExists(String email) throws SQLException {
        final String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
