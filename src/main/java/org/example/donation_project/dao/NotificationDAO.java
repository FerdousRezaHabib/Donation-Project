package org.example.donation_project.dao;

import org.example.donation_project.model.Notification;
import org.example.donation_project.db.Db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public List<Notification> listUnread(long userId, int limit) throws SQLException {
        final String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = 0 LIMIT ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {
                List<Notification> notifications = new ArrayList<>();
                while (rs.next()) {
                    notifications.add(map(rs));
                }
                return notifications;
            }
        }
    }

    public void markRead(long notificationId) throws SQLException {
        final String sql = "UPDATE notifications SET is_read = 1 WHERE id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, notificationId);
            ps.executeUpdate();
        }
    }

    private Notification map(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String message = rs.getString("message");
        boolean isRead = rs.getInt("is_read") == 1;
        return new Notification(id, title, message, isRead);
    }
}
