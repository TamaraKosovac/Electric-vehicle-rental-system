package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.ManagerDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerDAO {

    public static ManagerDTO login(String username, String password) {
        String query = "SELECT u.id, u.username, u.first_name, u.last_name " +
                "FROM user u " +
                "JOIN employee e ON u.id = e.id " +
                "WHERE u.username=? AND u.password=? AND e.role='MANAGER'";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ManagerDTO(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
