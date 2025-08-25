module org.example.donation_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires java.sql;
    requires jbcrypt;

    // FXML needs reflective access to controllers:
    opens org.example.donation_project.controller to javafx.fxml;

    // (Optional) if you use @FXML in classes in the root package:
    opens org.example.donation_project to javafx.fxml;

    // Expose APIs (optional for controllers, but handy if other modules refer to them):
    exports org.example.donation_project;
    exports org.example.donation_project.controller;
}
