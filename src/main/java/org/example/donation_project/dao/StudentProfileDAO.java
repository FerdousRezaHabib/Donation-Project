package org.example.donation_project.dao;

import org.example.donation_project.model.StudentProfile;
import org.example.donation_project.db.Db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentProfileDAO {

    // Create or update the student profile in the database
    public void createOrUpdate(StudentProfile profile) throws SQLException {
        final String sql = """
            INSERT INTO student_profile (user_id, name, school, class_year, bio, photo_url, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
            name = ?, school = ?, class_year = ?, bio = ?, photo_url = ?, updated_at = ?;
        """;

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters for the INSERT or UPDATE query
            ps.setLong(1, profile.getUserId());
            ps.setString(2, profile.getName());
            ps.setString(3, profile.getSchool());
            ps.setShort(4, profile.getClassYear());
            ps.setString(5, profile.getBio());
            ps.setString(6, profile.getPhotoUrl());
            ps.setTimestamp(7, Timestamp.valueOf(profile.getCreatedAt()));
            ps.setTimestamp(8, Timestamp.valueOf(profile.getUpdatedAt()));

            // On duplicate key, update these fields
            ps.setString(9, profile.getName());
            ps.setString(10, profile.getSchool());
            ps.setShort(11, profile.getClassYear());
            ps.setString(12, profile.getBio());
            ps.setString(13, profile.getPhotoUrl());
            ps.setTimestamp(14, Timestamp.valueOf(profile.getUpdatedAt()));

            // Execute the insert/update query
            ps.executeUpdate();
        }
    }

    // Find a student profile by user ID
    public Optional<StudentProfile> findByUserId(long userId) throws SQLException {
        final String sql = "SELECT * FROM student_profile WHERE user_id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StudentProfile profile = map(rs);
                    return Optional.of(profile);
                }
                return Optional.empty();
            }
        }
    }

    // List all approved students with paging and optional filters
    public List<StudentProfile> listApprovedStudents(int limit, int offset) throws SQLException {
        final String sql = """
            SELECT sp.* FROM student_profile sp
            JOIN users u ON sp.user_id = u.id
            WHERE u.status = 'active' AND u.soft_deleted = 0
            ORDER BY sp.created_at DESC
            LIMIT ? OFFSET ?;
        """;

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            List<StudentProfile> profiles = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    profiles.add(map(rs));
                }
            }
            return profiles;
        }
    }

    // Utility method to map ResultSet to StudentProfile object
    private StudentProfile map(ResultSet rs) throws SQLException {
        StudentProfile profile = new StudentProfile();
        profile.setUserId(rs.getLong("user_id"));
        profile.setName(rs.getString("name"));
        profile.setSchool(rs.getString("school"));
        profile.setClassYear(rs.getShort("class_year"));
        profile.setBio(rs.getString("bio"));
        profile.setPhotoUrl(rs.getString("photo_url"));
        profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return profile;
    }

    // Soft delete a student profile (set soft_deleted = 1)
    public void softDelete(long userId) throws SQLException {
        final String sql = "UPDATE student_profile sp JOIN users u ON sp.user_id = u.id SET u.soft_deleted = 1 WHERE u.id = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        }
    }

}
