package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.beans.ScooterBean;
import org.unibl.etf.ip.erent.beans.ManufacturerBean;
import org.unibl.etf.ip.erent.dto.ScooterDTO;
import org.unibl.etf.ip.erent.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScooterDAO {

    public static List<ScooterBean> findAll() {
        List<ScooterBean> result = new ArrayList<>();
        String sql = "SELECT s.*, v.*, m.* " +
                "FROM scooter s " +
                "JOIN vehicle v ON s.id = v.id " +
                "JOIN manufacturer m ON v.manufacturer_id = m.id";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public static List<ScooterBean> findAvailable() {
        List<ScooterBean> result = new ArrayList<>();
        String sql = "SELECT s.*, v.*, m.* " +
                "FROM scooter s " +
                "JOIN vehicle v ON s.id = v.id " +
                "JOIN manufacturer m ON v.manufacturer_id = m.id " +
                "WHERE v.state = 'AVAILABLE'";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public static List<ScooterDTO> findAvailableDTO() {
        List<ScooterDTO> result = new ArrayList<>();
        for (ScooterBean scooter : findAvailable()) {
            ScooterDTO dto = new ScooterDTO();
            dto.setId(scooter.getId());
            dto.setUniqueId(scooter.getUniqueId());
            dto.setModel(scooter.getModel());
            dto.setManufacturerName(scooter.getManufacturer().getName());
            result.add(dto);
        }
        return result;
    }

    private static ScooterBean mapRow(ResultSet rs) throws SQLException {
        ScooterBean scooter = new ScooterBean();
        scooter.setId(rs.getLong("id"));
        scooter.setUniqueId(rs.getString("unique_id"));
        scooter.setModel(rs.getString("model"));
        scooter.setPurchasePrice(rs.getDouble("purchase_price"));
        scooter.setImagePath(rs.getString("image_path"));
        scooter.setState(rs.getString("state"));
        scooter.setCurrentLatitude(rs.getDouble("current_latitude"));
        scooter.setCurrentLongitude(rs.getDouble("current_longitude"));
        scooter.setMaxSpeed(rs.getInt("max_speed"));

        ManufacturerBean m = new ManufacturerBean();
        m.setId(rs.getLong("manufacturer_id"));
        m.setName(rs.getString("name"));
        m.setCountry(rs.getString("country"));
        m.setAddress(rs.getString("address"));
        m.setPhone(rs.getString("phone"));
        m.setFax(rs.getString("fax"));
        m.setEmail(rs.getString("email"));
        scooter.setManufacturer(m);

        return scooter;
    }
}