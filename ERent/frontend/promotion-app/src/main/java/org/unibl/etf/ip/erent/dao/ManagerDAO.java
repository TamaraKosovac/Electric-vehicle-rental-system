package org.unibl.etf.ip.erent.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.unibl.etf.ip.erent.dto.ManagerDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerDAO {

    public static ManagerDTO login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return null;
        }

        String query = "SELECT u.id, u.username, u.password, u.first_name, u.last_name " +
                "FROM user u " +
                "JOIN employee e ON u.id = e.id " +
                "WHERE u.username=? AND e.role='MANAGER'";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");

                    if (BCrypt.checkpw(password, hashedPassword)) {
                        return new ManagerDTO(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("first_name"),
                                rs.getString("last_name")
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}