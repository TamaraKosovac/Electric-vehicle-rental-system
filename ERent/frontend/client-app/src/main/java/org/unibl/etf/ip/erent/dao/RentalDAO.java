package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.RentalDTO;
import org.unibl.etf.ip.erent.util.DBUtil;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
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
                    Timestamp endTs = rs.getTimestamp("end_date_time");

                    rentals.add(new RentalDTO(
                            rs.getLong("id"),
                            rs.getString("manufacturer"),
                            rs.getString("model"),
                            rs.getTimestamp("start_date_time").toLocalDateTime(),
                            endTs != null ? endTs.toLocalDateTime() : null,
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

    public static Long startRental(int clientId, Long vehicleId,
                                   double latitude, double longitude,
                                   double pricePerHour) {
        String query = "INSERT INTO rental (client_id, vehicle_id, start_date_time, " +
                "start_latitude, start_longitude, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime now = LocalDateTime.now();
            ps.setInt(1, clientId);
            ps.setLong(2, vehicleId);
            ps.setTimestamp(3, Timestamp.valueOf(now));
            ps.setDouble(4, latitude);
            ps.setDouble(5, longitude);
            ps.setDouble(6, 1.0);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean finishRental(Long rentalId,
                                       double endLatitude, double endLongitude,
                                       double pricePerHour) {
        String query = "UPDATE rental SET end_date_time = ?, end_latitude = ?, end_longitude = ?, " +
                "duration = ?, price = ? WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            LocalDateTime start = null;
            try (PreparedStatement ps2 = con.prepareStatement("SELECT start_date_time FROM rental WHERE id=?")) {
                ps2.setLong(1, rentalId);
                try (ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) {
                        start = rs.getTimestamp("start_date_time").toLocalDateTime();
                    }
                }
            }

            if (start == null) return false;

            LocalDateTime end = LocalDateTime.now();
            double durationHours = Duration.between(start, end).toMinutes() / 60.0;
            double totalPrice = 1.0 + (durationHours * pricePerHour);

            ps.setTimestamp(1, Timestamp.valueOf(end));
            ps.setDouble(2, endLatitude);
            ps.setDouble(3, endLongitude);
            ps.setDouble(4, durationHours);
            ps.setDouble(5, totalPrice);
            ps.setLong(6, rentalId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}