//package org.example.donation_project.model;
//
//import java.time.LocalDateTime;
//
//public class User {
//
//    public enum Role { student, donor, moderator }
//    public enum Status { pending, active, rejected, blocked }
//
//    private long id;
//    private String email;
//    private String passwordHash;
//    private Role role;
//    private Status status;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    public User() {}
//
//    public User(long id, String email, String passwordHash, Role role, Status status,
//                LocalDateTime createdAt, LocalDateTime updatedAt) {
//        this.id = id;
//        this.email = email;
//        this.passwordHash = passwordHash;
//        this.role = role;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
//
//    public static User forInsert(String email, String passwordHash, Role role, Status status) {
//        User u = new User();
//        u.email = email;
//        u.passwordHash = passwordHash;
//        u.role = role;
//        u.status = status;
//        u.createdAt = LocalDateTime.now();
//        u.updatedAt = LocalDateTime.now();
//        return u;
//    }
//
//    public long getId() { return id; }
//    public void setId(long id) { this.id = id; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPasswordHash() { return passwordHash; }
//    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
//
//    public Role getRole() { return role; }
//    public void setRole(Role role) { this.role = role; }
//
//    public Status getStatus() { return status; }
//    public void setStatus(Status status) { this.status = status; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//
//    public LocalDateTime getUpdatedAt() { return updatedAt; }
//    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
//
//    @Override
//    public String toString() {
//        return "User{id=" + id +
//                ", email='" + email + '\'' +
//                ", role=" + role +
//                ", status=" + status +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                '}';
//    }
//}
package org.example.donation_project.model;

import java.time.LocalDateTime;

public class User {

    private long id;
    private String email;
    private String passwordHash;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean softDeleted;

    // Enum for User roles
    public enum Role {
        STUDENT, DONOR, MODERATOR
    }

    // Enum for User status
    public enum Status {
        PENDING, ACTIVE, REJECTED, BLOCKED
    }

    // Empty constructor
    public User() {
        // Default initialization if needed
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.softDeleted = false;
    }

    // Constructor for creating a User object with provided values
    public User(String email, String passwordHash, Role role, Status status) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.softDeleted = false;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }
}

