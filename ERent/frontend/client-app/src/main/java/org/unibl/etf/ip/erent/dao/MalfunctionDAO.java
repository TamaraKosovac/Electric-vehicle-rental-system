package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.beans.MalfunctionBean;
import org.unibl.etf.ip.erent.util.DBUtil;
import java.sql.*;
import java.util.*;

public class MalfunctionDAO {

    public static List<MalfunctionBean> findByVehicle(Long vehicleId) {
        List<MalfunctionBean> result = new ArrayList<>();
        String sql = "SELECT * FROM malfunction WHERE vehicle_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, vehicleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MalfunctionBean mf = new MalfunctionBean();
                mf.setId(rs.getLong("id"));
                mf.setDescription(rs.getString("description"));
                mf.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                mf.setVehicleId(rs.getLong("vehicle_id"));
                result.add(mf);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }
}