package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.beans.CarBean;
import org.unibl.etf.ip.erent.beans.ManufacturerBean;
import org.unibl.etf.ip.erent.dto.CarDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    public static List<CarBean> findAll() {
        List<CarBean> result = new ArrayList<>();
        String sql = "SELECT c.*, v.*, m.* " +
                "FROM car c " +
                "JOIN vehicle v ON c.id = v.id " +
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

    public static List<CarBean> findAvailable() {
        List<CarBean> result = new ArrayList<>();
        String sql = "SELECT c.*, v.*, m.* " +
                "FROM car c " +
                "JOIN vehicle v ON c.id = v.id " +
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

    public static List<CarDTO> findAvailableDTO() {
        List<CarDTO> result = new ArrayList<>();
        for (CarBean car : findAvailable()) {
            CarDTO dto = new CarDTO();
            dto.setId(car.getId());
            dto.setUniqueId(car.getUniqueId());
            dto.setModel(car.getModel());
            dto.setManufacturerName(car.getManufacturer().getName());
            result.add(dto);
        }
        return result;
    }

    private static CarBean mapRow(ResultSet rs) throws SQLException {
        CarBean car = new CarBean();
        car.setId(rs.getLong("id"));
        car.setUniqueId(rs.getString("unique_id"));
        car.setModel(rs.getString("model"));
        car.setPurchasePrice(rs.getDouble("purchase_price")); // cijena nabavke (ne rental!)
        car.setImagePath(rs.getString("image_path"));
        car.setState(rs.getString("state"));
        car.setCurrentLatitude(rs.getDouble("current_latitude"));
        car.setCurrentLongitude(rs.getDouble("current_longitude"));
        car.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
        car.setDescription(rs.getString("description"));

        ManufacturerBean m = new ManufacturerBean();
        m.setId(rs.getLong("manufacturer_id"));
        m.setName(rs.getString("name"));
        m.setCountry(rs.getString("country"));
        m.setAddress(rs.getString("address"));
        m.setPhone(rs.getString("phone"));
        m.setFax(rs.getString("fax"));
        m.setEmail(rs.getString("email"));
        car.setManufacturer(m);

        return car;
    }
}