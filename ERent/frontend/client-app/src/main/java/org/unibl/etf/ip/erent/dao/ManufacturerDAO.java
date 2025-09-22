package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.beans.ManufacturerBean;
import org.unibl.etf.ip.erent.util.DBUtil;
import java.sql.*;
import java.util.*;

public class ManufacturerDAO {

    public static List<ManufacturerBean> findAll() {
        List<ManufacturerBean> result = new ArrayList<>();
        String sql = "SELECT * FROM manufacturer";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ManufacturerBean m = new ManufacturerBean();
                m.setId(rs.getLong("id"));
                m.setName(rs.getString("name"));
                m.setCountry(rs.getString("country"));
                m.setAddress(rs.getString("address"));
                m.setPhone(rs.getString("phone"));
                m.setFax(rs.getString("fax"));
                m.setEmail(rs.getString("email"));
                result.add(m);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public static ManufacturerBean findById(Long id) {
        ManufacturerBean m = null;
        String sql = "SELECT * FROM manufacturer WHERE id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                m = new ManufacturerBean();
                m.setId(rs.getLong("id"));
                m.setName(rs.getString("name"));
                m.setCountry(rs.getString("country"));
                m.setAddress(rs.getString("address"));
                m.setPhone(rs.getString("phone"));
                m.setFax(rs.getString("fax"));
                m.setEmail(rs.getString("email"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return m;
    }
}