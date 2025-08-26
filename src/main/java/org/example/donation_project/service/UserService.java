package org.example.donation_project.service;

import org.example.donation_project.dao.UserDAO;
import org.example.donation_project.model.User;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Get user by their email.
     * @param email The email of the user
     * @return An Optional containing the User if found, or empty if not found
     */
    public Optional<User> getUserByEmail(String email) {
        try {
            return userDAO.findByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Get user by their ID.
     * @param userId The ID of the user
     * @return An Optional containing the User if found, or empty if not found
     */
    public Optional<User> getUserById(long userId) {
        try {
            return userDAO.findById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Create a new user (register a new user).
     * @param email The email of the user
     * @param password The password of the user
     * @param role The role of the user (student, donor, moderator)
     * @param status The initial status of the user
     * @return The user ID after creation
     */
    public long createUser(String email, String password, User.Role role, User.Status status) {
        try {
            // Encrypt password here using BCrypt
            String passwordHash = password; // Use bcrypt hash for password in a real application
            User user = new User(email, passwordHash, role, status);
            return userDAO.createUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  // Return an invalid ID to indicate failure
        }
    }

    /**
     * Check if a user exists by email.
     * @param email The email of the user
     * @return true if the email exists, false otherwise
     */
    public boolean emailExists(String email) {
        try {
            return userDAO.emailExists(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update the status of a user (e.g., active, blocked, etc.).
     * @param userId The ID of the user to update
     * @param status The new status to set
     */
    public void updateStatus(long userId, User.Status status) {
        try {
            userDAO.updateStatus(userId, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the password of a user.
     * @param userId The ID of the user
     * @param newPassword The new password
     */
    public void updatePassword(long userId, String newPassword) {
        try {
            // Encrypt password here using BCrypt
            String newPasswordHash = newPassword; // Use bcrypt hash for password in a real application
            userDAO.updatePasswordHash(userId, newPasswordHash);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Soft delete a user (set soft_deleted = 1).
     * @param userId The ID of the user to soft delete
     */
    public void softDeleteUser(long userId) {
        try {
            userDAO.softDelete(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
