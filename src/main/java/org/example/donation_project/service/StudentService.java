package org.example.donation_project.service;

import org.example.donation_project.dao.StudentProfileDAO;
import org.example.donation_project.model.StudentProfile;
import org.example.donation_project.model.User;
import org.example.donation_project.dao.UserDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling student-related business logic.
 */
public class StudentService {

    private final StudentProfileDAO studentProfileDAO;
    private final UserDAO userDAO;

    public StudentService() {
        // Initialize DAO objects
        this.studentProfileDAO = new StudentProfileDAO();
        this.userDAO = new UserDAO();
    }

    /**
     * Get the profile of a student by their user ID.
     * @param userId The user ID of the student
     * @return The student's profile wrapped in an Optional
     */
    public Optional<StudentProfile> getMyProfile(long userId) {
        try {
            // Fetch the profile using the DAO
            return studentProfileDAO.findByUserId(userId);
        } catch (SQLException e) {
            // Handle SQL exception, perhaps log the error
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * List all approved students with pagination.
     * @param limit The number of students to return
     * @param offset The offset for pagination
     * @return List of approved students
     */
    public List<StudentProfile> listApprovedStudents(int limit, int offset) {
        try {
            // Fetch approved students using the DAO
            return studentProfileDAO.listApprovedStudents(limit, offset);
        } catch (SQLException e) {
            // Handle SQL exception, perhaps log the error
            e.printStackTrace();
            return List.of(); // Return empty list in case of error
        }
    }

    /**
     * Update the profile information of the student.
     * @param userId The user ID of the student
     * @param name The updated name of the student
     * @param school The updated school name
     * @param classYear The updated class year
     * @param bio The updated bio
     * @param photoUrl The updated photo URL
     * @return True if the profile was updated successfully, false otherwise
     */
    public boolean updateProfile(long userId, String name, String school, short classYear, String bio, String photoUrl) {
        try {
            // Fetch the student's current profile
            Optional<StudentProfile> profileOpt = studentProfileDAO.findByUserId(userId);

            if (profileOpt.isPresent()) {
                StudentProfile profile = profileOpt.get();
                // Update profile fields
                profile.setName(name);
                profile.setSchool(school);
                profile.setClassYear(classYear);
                profile.setBio(bio);
                profile.setPhotoUrl(photoUrl);
                profile.setUpdatedAt(java.time.LocalDateTime.now()); // Set the updated time

                // Save the updated profile to the database
                studentProfileDAO.createOrUpdate(profile);
                return true;
            }
            return false;
        } catch (SQLException e) {
            // Handle SQL exception, log the error
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Activate a student's account (for example, after approval by a moderator).
     * @param userId The user ID of the student
     * @return True if the student was activated successfully, false otherwise
     */
    public boolean activateStudent(long userId) {
        try {
            // Get the user and update the status to 'active'
            Optional<User> userOpt = userDAO.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setStatus(User.Status.ACTIVE); // Set user status to 'active'
                userDAO.updateStatus(userId, User.Status.ACTIVE); // Update status in the database

                // Update the student profile's status
                Optional<StudentProfile> profileOpt = studentProfileDAO.findByUserId(userId);
                if (profileOpt.isPresent()) {
                    StudentProfile profile = profileOpt.get();
                    profile.setUpdatedAt(java.time.LocalDateTime.now());
                    studentProfileDAO.createOrUpdate(profile); // Update profile
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            // Handle SQL exception, log the error
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reject a student's account, marking the profile as 'rejected' in the user table.
     * @param userId The user ID of the student
     * @return True if the student was rejected successfully, false otherwise
     */
    public boolean rejectStudent(long userId) {
        try {
            // Get the user and update the status to 'rejected'
            Optional<User> userOpt = userDAO.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setStatus(User.Status.REJECTED); // Set user status to 'rejected'
                userDAO.updateStatus(userId, User.Status.REJECTED); // Update status in the database

                // Optionally, you may want to delete the student profile or mark it as rejected
                Optional<StudentProfile> profileOpt = studentProfileDAO.findByUserId(userId);
                if (profileOpt.isPresent()) {
                    StudentProfile profile = profileOpt.get();
                    profile.setUpdatedAt(java.time.LocalDateTime.now());
                    studentProfileDAO.createOrUpdate(profile); // Update profile
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            // Handle SQL exception, log the error
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Soft delete a student's profile and user account.
     * @param userId The user ID of the student
     * @return True if the student was deleted successfully, false otherwise
     */
    public boolean softDeleteStudent(long userId) {
        try {
            // Soft delete the user (set soft_deleted = 1)
            userDAO.softDelete(userId);

            // Soft delete the student profile
            studentProfileDAO.softDelete(userId);
            return true;
        } catch (SQLException e) {
            // Handle SQL exception, log the error
            e.printStackTrace();
            return false;
        }
    }
}
