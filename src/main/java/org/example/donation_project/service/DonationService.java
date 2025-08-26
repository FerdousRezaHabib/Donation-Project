package org.example.donation_project.service;

import org.example.donation_project.dao.DonationDAO;
import org.example.donation_project.model.Donation;

import java.sql.SQLException;
import java.util.List;

public class DonationService {

    private final DonationDAO donationDAO;

    public DonationService() {
        donationDAO = new DonationDAO();
    }

    /**
     * Fetch the list of donations received by a donor.
     * @param donorId The ID of the donor
     * @return List of donations received by the donor
     */
    public List<Donation> getReceivedDonations(long donorId) {
        try {
            // Retrieve donations from the DAO
            return donationDAO.listByDonor(donorId, 10); // 10 is just an example for pagination
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Return an empty list in case of an error
        }
    }
}
