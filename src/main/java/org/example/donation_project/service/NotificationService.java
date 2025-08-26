package org.example.donation_project.service;

import org.example.donation_project.dao.NotificationDAO;
import org.example.donation_project.model.Notification;

import java.sql.SQLException;
import java.util.List;

public class NotificationService {

    private final NotificationDAO notificationDAO;

    public NotificationService() {
        notificationDAO = new NotificationDAO();
    }

    /**
     * Fetch the unread notifications for a user.
     * @param userId The ID of the user (donor)
     * @param limit The maximum number of notifications to fetch
     * @return List of unread notifications
     */
    public List<Notification> listUnread(long userId, int limit) {
        try {
            return notificationDAO.listUnread(userId, limit);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Return an empty list if an error occurs
        }
    }

    /**
     * Mark a notification as read.
     * @param notificationId The ID of the notification to mark as read
     */
    public void markRead(long notificationId) {
        try {
            notificationDAO.markRead(notificationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
