package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.RentalDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    public static List<RentalDTO> getRentalsByClient(int clientId) {
        List<RentalDTO> rentals = new ArrayList<>();

        String query = "SELECT r.id, r.start_date_time, r.end_date_time, r.duration, r.price, " +
                "r.start_latitude, r.start_longitude, r.end_latitude, r.end_longitude, " +
                "v.model, m.name AS manufacturer " +
                "FROM rental r " +
                "JOIN vehicle v ON r.vehicle_id = v.id " +
                "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                "WHERE r.client_id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, clientId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rentals.add(new RentalDTO(
                            rs.getLong("id"),
                            rs.getString("manufacturer"),
                            rs.getString("model"),
                            rs.getTimestamp("start_date_time").toLocalDateTime(),
                            rs.getTimestamp("end_date_time").toLocalDateTime(),
                            rs.getDouble("duration"),
                            rs.getDouble("price"),
                            rs.getDouble("start_latitude"),
                            rs.getDouble("start_longitude"),
                            rs.getDouble("end_latitude"),
                            rs.getDouble("end_longitude")
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rentals;
    }
}