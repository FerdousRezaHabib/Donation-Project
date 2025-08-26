
//CHANGE-1

//package org.example.donation_project;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.net.URL;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        // Load the login view
//        FXMLLoader loader = new FXMLLoader(Main.class.getResource(
//                "/org/example/donation_project/fxml/Login.fxml"
//        ));
//        Parent root = loader.load();
//
//        Scene scene = new Scene(root);
//
//        // Try to load light/dark theme if present (skips silently if missing)
//        addStylesheetIfPresent(scene, "/org/example/donation_project/css/app-light.css");
//        String themeProp = System.getProperty("app.theme", "").trim().toLowerCase();
//        if ("dark".equals(themeProp)) {
//            addStylesheetIfPresent(scene, "/org/example/donation_project/css/app-dark.css");
//        }
//
//        stage.setTitle("Donation App • Sign in");
//        stage.setScene(scene);
//        stage.sizeToScene();
//        stage.centerOnScreen();
//        stage.show();
//    }
//
//    private static void addStylesheetIfPresent(Scene scene, String resourcePath) {
//        URL url = Main.class.getResource(resourcePath);
//        if (url != null) {
//            scene.getStylesheets().add(url.toExternalForm());
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}




//CHANGE-2

//public class Main extends Application {
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        // Load the login screen initially (you can start with login, or choose signup)
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/donation_project/fxml/Login.fxml"));
//        Scene scene = new Scene(loader.load());
//
//        stage.setTitle("Donation App");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    // Optionally, add a method to show the signup screen
//    public void showSignup() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/donation_project/fxml/Signup.fxml"));
//        Scene scene = new Scene(loader.load());
//
//        Stage stage = new Stage();
//        stage.setTitle("Signup");
//        stage.setScene(scene);
//        stage.show();
//    }
//}


package org.example.donation_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.donation_project.db.DbInitializer;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize the database (create tables if they don't exist)
        try {
            DbInitializer.initialize();  // Initialize the database
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }

        // Load the login screen initially
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/donation_project/fxml/Login.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Donation App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


