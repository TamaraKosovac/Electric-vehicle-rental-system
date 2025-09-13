package org.unibl.etf.ip.erent.dao;

import org.mindrot.jbcrypt.BCrypt;
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
                "WHERE u.username=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");

                    if (BCrypt.checkpw(password, hashedPassword)) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}