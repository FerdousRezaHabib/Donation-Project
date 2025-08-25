package org.example.donation_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the login view
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(
                "/org/example/donation_project/fxml/Login.fxml"
        ));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        // Try to load light/dark theme if present (skips silently if missing)
        addStylesheetIfPresent(scene, "/org/example/donation_project/css/app-light.css");
        String themeProp = System.getProperty("app.theme", "").trim().toLowerCase();
        if ("dark".equals(themeProp)) {
            addStylesheetIfPresent(scene, "/org/example/donation_project/css/app-dark.css");
        }

        stage.setTitle("Donation App • Sign in");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    private static void addStylesheetIfPresent(Scene scene, String resourcePath) {
        URL url = Main.class.getResource(resourcePath);
        if (url != null) {
            scene.getStylesheets().add(url.toExternalForm());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
