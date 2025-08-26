package org.example.donation_project.model;

import java.time.LocalDateTime;

/**
 * Represents the student profile information associated with a user.
 */
public class StudentProfile {

    private long userId;          // ID of the associated user
    private String name;          // Name of the student
    private String school;        // School of the student
    private short classYear;      // Year of the student (e.g., 2023)
    private String bio;           // Short biography of the student
    private String photoUrl;      // URL to the student's profile picture
    private LocalDateTime createdAt; // Date when the profile was created
    private LocalDateTime updatedAt; // Date when the profile was last updated

    // Getters and Setters

    /**
     * Get the user ID.
     * @return userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Set the user ID for this student profile.
     * @param userId user ID to be set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Get the name of the student.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the student.
     * @param name name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the school of the student.
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * Set the school of the student.
     * @param school school name
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Get the class year of the student.
     * @return classYear
     */
    public short getClassYear() {
        return classYear;
    }

    /**
     * Set the class year of the student.
     * @param classYear class year (e.g., 2023)
     */
    public void setClassYear(short classYear) {
        this.classYear = classYear;
    }

    /**
     * Get the biography of the student.
     * @return bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Set the biography of the student.
     * @param bio biography text
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Get the URL to the student's profile picture.
     * @return photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Set the URL to the student's profile picture.
     * @param photoUrl URL to the student's profile picture
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * Get the date when the profile was created.
     * @return createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the date when the profile was created.
     * @param createdAt profile creation date
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Get the date when the profile was last updated.
     * @return updatedAt
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Set the date when the profile was last updated.
     * @param updatedAt profile last updated date
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility Methods

    /**
     * Converts the current object to a string representation.
     * @return string representation of the StudentProfile
     */
    @Override
    public String toString() {
        return "StudentProfile{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", classYear=" + classYear +
                ", bio='" + bio + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
