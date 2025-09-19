package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.beans.BikeBean;
import org.unibl.etf.ip.erent.beans.ManufacturerBean;
import org.unibl.etf.ip.erent.dto.BikeDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BikeDAO {

    public static List<BikeBean> findAll() {
        List<BikeBean> result = new ArrayList<>();
        String sql = "SELECT b.*, v.*, m.* " +
                "FROM bike b " +
                "JOIN vehicle v ON b.id = v.id " +
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

    public static List<BikeBean> findAvailable() {
        List<BikeBean> result = new ArrayList<>();
        String sql = "SELECT b.*, v.*, m.* " +
                "FROM bike b " +
                "JOIN vehicle v ON b.id = v.id " +
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

    public static List<BikeDTO> findAvailableDTO() {
        List<BikeDTO> result = new ArrayList<>();
        for (BikeBean bike : findAvailable()) {
            BikeDTO dto = new BikeDTO();
            dto.setId(bike.getId());
            dto.setUniqueId(bike.getUniqueId());
            dto.setModel(bike.getModel());
            dto.setManufacturerName(bike.getManufacturer().getName());
            result.add(dto);
        }
        return result;
    }

    private static BikeBean mapRow(ResultSet rs) throws SQLException {
        BikeBean bike = new BikeBean();
        bike.setId(rs.getLong("id"));
        bike.setUniqueId(rs.getString("unique_id"));
        bike.setModel(rs.getString("model"));
        bike.setPurchasePrice(rs.getDouble("purchase_price"));
        bike.setImagePath(rs.getString("image_path"));
        bike.setState(rs.getString("state"));
        bike.setCurrentLatitude(rs.getDouble("current_latitude"));
        bike.setCurrentLongitude(rs.getDouble("current_longitude"));
        bike.setAutonomy(rs.getInt("autonomy"));

        ManufacturerBean m = new ManufacturerBean();
        m.setId(rs.getLong("manufacturer_id"));
        m.setName(rs.getString("name"));
        m.setCountry(rs.getString("country"));
        m.setAddress(rs.getString("address"));
        m.setPhone(rs.getString("phone"));
        m.setFax(rs.getString("fax"));
        m.setEmail(rs.getString("email"));
        bike.setManufacturer(m);

        return bike;
    }
}