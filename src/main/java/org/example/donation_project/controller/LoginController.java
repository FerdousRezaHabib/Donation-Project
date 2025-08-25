package org.example.donation_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public final class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    @FXML private Hyperlink forgotLink;
    @FXML private Hyperlink signupLink;

    @FXML
    private void initialize() {
        // optional: any UI setup
        if (errorLabel != null) {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        }
    }

    @FXML
    private void handleLogin(ActionEvent e) {
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String pass  = passwordField.getText() == null ? "" : passwordField.getText();

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
            // TODO: navigate to dashboard
            System.out.println("Logged in as " + email);
        }
    }

    @FXML
    private void handleForgotPassword(ActionEvent e) {
        // TODO: open forgot password dialog/scene
        System.out.println("Forgot password clicked");
    }

    @FXML
    private void handleOpenSignup(ActionEvent e) {
        // TODO: navigate to signup scene
        System.out.println("Open signup clicked");
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
