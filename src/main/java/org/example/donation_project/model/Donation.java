package org.example.donation_project.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Donation {

    private final LongProperty donationId;
    private final DoubleProperty amount;
    private final StringProperty donationDate;

    public Donation(long donationId, double amount, LocalDateTime donationDate) {
        this.donationId = new SimpleLongProperty(donationId);
        this.amount = new SimpleDoubleProperty(amount);
        this.donationDate = new SimpleStringProperty(donationDate.toString());
    }

    public long getDonationId() {
        return donationId.get();
    }

    public LongProperty donationIdProperty() {
        return donationId;
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public String getDonationDate() {
        return donationDate.get();
    }

    public StringProperty donationDateProperty() {
        return donationDate;
    }
}
