package org.unibl.etf.ip.erent.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.unibl.etf.ip.erent.beans.ClientBean;
import org.unibl.etf.ip.erent.dto.ClientDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;

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

    public static boolean register(ClientBean client) {
        String insertUser = "INSERT INTO user (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
        String insertClient = "INSERT INTO client (id, document_type, document_number, driving_license, email, phone, avatar_path, blocked) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement psUser = con.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {

            String hashedPassword = BCrypt.hashpw(client.getPassword(), BCrypt.gensalt());

            psUser.setString(1, client.getFirstName());
            psUser.setString(2, client.getLastName());
            psUser.setString(3, client.getUsername());
            psUser.setString(4, hashedPassword);

            int affectedRows = psUser.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creating user failed, no rows affected.");

            int userId;
            try (ResultSet generatedKeys = psUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            try (PreparedStatement psClient = con.prepareStatement(insertClient)) {
                psClient.setInt(1, userId);
                psClient.setString(2, client.getDocumentType());
                psClient.setString(3, client.getDocumentNumber());
                psClient.setString(4, client.getDrivingLicense());
                psClient.setString(5, client.getEmail());
                psClient.setString(6, client.getPhone());
                psClient.setString(7, client.getAvatarPath());
                psClient.setBoolean(8, false);
                psClient.executeUpdate();
            }

            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate entry: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
