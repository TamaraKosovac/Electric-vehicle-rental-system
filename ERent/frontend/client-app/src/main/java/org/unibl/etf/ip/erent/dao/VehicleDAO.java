package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.beans.VehicleBean;
import org.unibl.etf.ip.erent.beans.ManufacturerBean;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;
import java.util.*;

public class VehicleDAO {

    public static List<VehicleBean> findAll() {
        List<VehicleBean> result = new ArrayList<>();
        String sql = "SELECT v.*, m.name as manufacturerName, m.country as manufacturerCountry " +
                "FROM vehicle v JOIN manufacturer m ON v.manufacturer_id = m.id";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                VehicleBean v = new VehicleBean();
                v.setId(rs.getLong("id"));
                v.setUniqueId(rs.getString("unique_id"));
                v.setModel(rs.getString("model"));
                v.setPurchasePrice(rs.getDouble("purchase_price"));
                v.setImagePath(rs.getString("image_path"));
                v.setState(rs.getString("state"));
                v.setCurrentLatitude(rs.getDouble("current_latitude"));
                v.setCurrentLongitude(rs.getDouble("current_longitude"));

                ManufacturerBean m = new ManufacturerBean();
                m.setId(rs.getLong("manufacturer_id"));
                m.setName(rs.getString("manufacturerName"));
                m.setCountry(rs.getString("manufacturerCountry"));
                v.setManufacturer(m);

                result.add(v);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }
}
