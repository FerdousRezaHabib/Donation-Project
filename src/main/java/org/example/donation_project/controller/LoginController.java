package org.example.donation_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public final class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink forgotLink;
    @FXML
    private Hyperlink signupLink;

    @FXML
    private void initialize() {
        // optional: any UI setup
        if (errorLabel != null) {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        }
    }

//    @FXML
//    private void handleLogin(ActionEvent e) {
//        String email = emailField.getText() == null ? "" : emailField.getText().trim();
//        String pass = passwordField.getText() == null ? "" : passwordField.getText();
//
//        if (email.isEmpty() || pass.isEmpty()) {
//            showError("Please enter both email and password.");
//            return;
//        }
//
//        // TODO: call your AuthService here
//        // boolean ok = authService.login(email, pass);
//        boolean ok = true; // placeholder for now
//
//        if (!ok) {
//            showError("Invalid email or password.");
//        } else {
//            hideError();
//            // TODO: navigate to dashboard
//            System.out.println("Logged in as " + email);
//        }
//    }

    @FXML
    private void handleLogin(ActionEvent e) {
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String pass = passwordField.getText() == null ? "" : passwordField.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            showError("Please enter both email and password.");
            return;
        }

        // TODO: call your AuthService here
        // boolean ok = authService.login(email, pass);
        boolean ok = true; // placeholder for now

        if (!ok) {
            showError("Invalid email or password.");
        } else {
            hideError();
            // Navigate to donor dashboard
            try {
                // Load the donor dashboard screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/donation_project/fxml/DonorDashboard.fxml"));

                // Check if the resource exists
                if (loader.getLocation() == null) {
                    showError("Dashboard file not found.");
                    return;
                }

                Parent root = loader.load();
                Scene dashboardScene = new Scene(root);

                // Close the current login window
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                // Create a new stage for the dashboard
                Stage dashboardStage = new Stage();
                dashboardStage.setTitle("Donor Dashboard");
                dashboardStage.setScene(dashboardScene);
                dashboardStage.show();

            } catch (IOException ex) {
                showError("Failed to load dashboard: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }



    @FXML
    private void handleForgotPassword(ActionEvent e) {
        // TODO: open forgot password dialog/scene
        System.out.println("Forgot password clicked");
    }

    //    @FXML
//    private void handleOpenSignup(ActionEvent e) {
//        // TODO: navigate to signup scene
//        System.out.println("Open signup clicked");
//    }
    @FXML
    private void handleOpenSignup(ActionEvent e) throws IOException {
        // Close the current login window
        Stage stage = (Stage) loginButton.getScene().getWindow(); // Assuming loginButton is in the current scene
        stage.close();

        // Load the signup screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/donation_project/fxml/Signup.fxml"));
        Scene signupScene = new Scene(loader.load());

        // Create a new stage (window) for the signup screen
        Stage signupStage = new Stage();
        signupStage.setTitle("Signup");
        signupStage.setScene(signupScene);
        signupStage.show();
    }


    private void showError(String msg) {
        if (errorLabel != null) {
            errorLabel.setText(msg);
            errorLabel.setManaged(true);
            errorLabel.setVisible(true);
        }
    }

    private void hideError() {
        if (errorLabel != null) {
            errorLabel.setManaged(false);
            errorLabel.setVisible(false);
            errorLabel.setText("");
        }
    }
}
