package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.RentalPriceDTO;
import org.unibl.etf.ip.erent.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RentalPriceDAO {

    public static RentalPriceDTO getByVehicleType(String vehicleType) {
        String query = "SELECT * FROM rental_price WHERE vehicle_type = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, vehicleType);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new RentalPriceDTO(
                            rs.getLong("id"),
                            rs.getString("vehicle_type"),
                            rs.getDouble("price_per_hour")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updatePrice(String vehicleType, double newPrice) {
        String query = "UPDATE rental_price SET price_per_hour = ? WHERE vehicle_type = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDouble(1, newPrice);
            ps.setString(2, vehicleType);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}