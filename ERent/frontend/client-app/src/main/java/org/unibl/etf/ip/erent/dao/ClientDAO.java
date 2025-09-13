package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.ClientDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDAO {

    public static ClientDTO login(String username, String password) {
        String query = "SELECT u.id, u.username, u.password, u.first_name, u.last_name, " +
                "c.email, c.phone, c.avatar_path, c.blocked " +
                "FROM user u " +
                "JOIN client c ON u.id = c.id " +
                "WHERE u.username=? AND u.password=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ClientDTO(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getBoolean("blocked")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
