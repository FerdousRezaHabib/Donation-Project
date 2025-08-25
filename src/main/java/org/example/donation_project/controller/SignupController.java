package org.example.donation_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.donation_project.dao.UserDAO;
import org.example.donation_project.model.User;

import java.io.IOException;


public class SignupController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;
    @FXML private Button signupButton;
    @FXML private Hyperlink loginLink;

    // Handle signup action when the user clicks the "Sign Up" button
    @FXML
    private void handleSignup(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Basic validation
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        // Create the user in the database
        UserDAO userDAO = new UserDAO();
        try {
            // Check if the email already exists
            if (userDAO.emailExists(email)) {
                showError("Email is already taken.");
                return;
            }

            // Create the new user in the database
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPasswordHash(password); // You should hash the password
            newUser.setRole(User.Role.student); // Assuming a default user role
            newUser.setStatus(User.Status.active); // Assuming active status

            long userId = userDAO.createUser(newUser); // Create user and return the generated ID

            // Proceed with successful signup logic
            hideError();
            // Optionally, close the current window and show a login screen
            closeSignupWindow();
            System.out.println("User created with ID: " + userId);

        } catch (Exception e) {
            showError("An error occurred while signing up. Please try again.");
            e.printStackTrace();
        }
    }

    // Handle the hyperlink to go back to the login screen
//    @FXML
//    private void handleLogin(ActionEvent event) {
//        // Close the current signup screen and open the login screen
//        closeSignupWindow();
//        // Code to show login screen can be added here
//    }
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        // Close the current signup window
        closeSignupWindow();

        // Load the login screen (Login.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/donation_project/fxml/Login.fxml"));
        Scene loginScene = new Scene(loader.load());

        // Create a new stage (window) for the login screen
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(loginScene);
        loginStage.show();
    }


    // Show error message in the error label
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setManaged(true);
        errorLabel.setVisible(true);
    }

    // Hide the error message
    private void hideError() {
        errorLabel.setManaged(false);
        errorLabel.setVisible(false);
    }

    // Close the signup window
    private void closeSignupWindow() {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        stage.close();
    }
}
