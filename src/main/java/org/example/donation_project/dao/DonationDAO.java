package org.example.donation_project.dao;

import org.example.donation_project.model.Donation;
import org.example.donation_project.db.Db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DonationDAO {

    public List<Donation> listByDonor(long donorId, int limit) throws SQLException {
        final String sql = "SELECT * FROM donations WHERE donor_id = ? LIMIT ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, donorId);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {
                List<Donation> donations = new ArrayList<>();
                while (rs.next()) {
                    donations.add(map(rs));
                }
                return donations;
            }
        }
    }

    private Donation map(ResultSet rs) throws SQLException {
        long donationId = rs.getLong("id");
        double amount = rs.getDouble("amount");
        LocalDateTime date = rs.getTimestamp("created_at").toLocalDateTime();
        return new Donation(donationId, amount, date);
    }
}
