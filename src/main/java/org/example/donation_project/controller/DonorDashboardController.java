package org.example.donation_project.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.donation_project.model.Donation;
import org.example.donation_project.model.Notification;
import org.example.donation_project.model.User;
import org.example.donation_project.service.DonationService;
import org.example.donation_project.service.NotificationService;
import org.example.donation_project.service.UserService;

import java.util.List;

public class DonorDashboardController {

    // FXML Elements
    @FXML private Label donorNameLabel;
    @FXML private Label donorStatusLabel;
    @FXML private ListView<Notification> notificationList;
    @FXML private TableView<Donation> receivedDonationsTable;
    @FXML private TableColumn<Donation, String> donationIdColumn;
    @FXML private TableColumn<Donation, Double> amountColumn;
    @FXML private TableColumn<Donation, String> dateColumn;
    @FXML private Button donateButton;
    @FXML private Label profileNameLabel;
    @FXML private Label profileSchoolLabel;
    @FXML private ImageView profileImage;

    // Service classes for business logic
    private UserService userService;
    private DonationService donationService;
    private NotificationService notificationService;

    // Donor data
    private User currentDonor;

    public DonorDashboardController() {
        userService = new UserService();
        donationService = new DonationService();
        notificationService = new NotificationService();
    }

    @FXML
    private void initialize() {
        // Load donor data from the database or session
        loadDonorData();
        loadNotifications();
        loadReceivedDonations();

        // Add functionality to the donate button
        donateButton.setOnAction(this::handleDonate);
    }

    // Load the current donor's data (name, status, profile)
    private void loadDonorData() {
        // Assume the current donor's ID is available (e.g., via session or logged-in user)
        long donorId = 1L;  // For testing, you should get this from session or authentication context

        // Fetch the donor details
        currentDonor = userService.getUserById(donorId).orElse(null);  // Replace with actual user fetching method

        if (currentDonor != null) {
            // Set the donor's name and status on the UI
            donorNameLabel.setText(currentDonor.getEmail());
            donorStatusLabel.setText(currentDonor.getStatus().toString());

            // Set up the donor's profile details
            profileNameLabel.setText(currentDonor.getEmail());
            profileSchoolLabel.setText("University of Example"); // You can modify it based on actual profile details
            // For testing, set a default image URL. You can change it dynamically as needed.
            profileImage.setImage(new Image("https://via.placeholder.com/150"));
        }
    }

    // Load notifications for the donor
    private void loadNotifications() {
        // For testing, assume we fetch notifications for the current donor
        List<Notification> notifications = notificationService.listUnread(currentDonor.getId(), 10);

        // Set up the notifications list view
        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList(notifications);
        notificationList.setItems(notificationObservableList);
    }

    // Load received donations for the donor
    private void loadReceivedDonations() {
        // Fetch donations for this donor. Assume donationService has the necessary method.
        List<Donation> donations = donationService.getReceivedDonations(currentDonor.getId());

        // Set up table columns with the donation data
        donationIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDonationId()))
        );
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().donationDateProperty());


        // Create an observable list for the table view
        ObservableList<Donation> donationObservableList = FXCollections.observableArrayList(donations);
        receivedDonationsTable.setItems(donationObservableList);
    }

    // Handle the "Donate" button click (donating to a student)
    @FXML
    private void handleDonate(ActionEvent event) {
        // Show a dialog or transition to a donation page to enter donation details
        System.out.println("Donate button clicked");
        // For simplicity, we just print to the console here.
        // In a real application, you would show a donation dialog (DonationDialog.fxml)
    }

    // When the user clicks on a donation, show the receipt in a dialog (optional)
    @FXML
    private void showDonationReceipt(Donation donation) {
        // Create and show a receipt dialog with the details of the donation
        System.out.println("Showing receipt for donation: " + donation.getDonationId());
        // You could open a new modal or pop-up dialog here with the donation receipt details
    }

    // Handle when a notification is clicked (mark it as read or show more details)
    @FXML
    private void handleNotificationClick(Notification notification) {
        // Mark the notification as read and hide it from the unread list
        notificationService.markRead(notification.getId());
        loadNotifications(); // Reload notifications to update the list
    }
}
