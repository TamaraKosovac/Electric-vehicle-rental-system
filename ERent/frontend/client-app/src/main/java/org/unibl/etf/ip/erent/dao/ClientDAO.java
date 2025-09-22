package org.unibl.etf.ip.erent.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.unibl.etf.ip.erent.beans.ClientBean;
import org.unibl.etf.ip.erent.dto.ClientDTO;
import org.unibl.etf.ip.erent.util.DBUtil;
import java.sql.*;

public class ClientDAO {

    public static ClientDTO login(String username, String password) {
        String query = "SELECT u.id, u.username, u.password, u.first_name, u.last_name, " +
                "c.document_type, c.document_number, c.driving_license, " +
                "c.email, c.phone, c.avatar_path, c.blocked, c.active " +
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
                        boolean active = rs.getBoolean("active");
                        if (!active) {
                            return null;
                        }

                        return new ClientDTO(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("document_type"),
                                rs.getString("document_number"),
                                rs.getString("driving_license"),
                                rs.getString("email"),
                                rs.getString("phone"),
                                rs.getString("avatar_path"),
                                rs.getBoolean("blocked"),
                                active
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
        String insertClient = "INSERT INTO client (id, document_type, document_number, driving_license, email, phone, avatar_path, blocked, active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                psClient.setBoolean(9, true);
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

    public static boolean deactivate(int clientId) {
        String query = "UPDATE client SET active = false WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, clientId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changePassword(int clientId, String oldPassword, String newPassword) {
        String selectQuery = "SELECT password FROM user WHERE id=?";
        String updateQuery = "UPDATE user SET password=? WHERE id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement psSelect = con.prepareStatement(selectQuery)) {

            psSelect.setInt(1, clientId);
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    String currentHash = rs.getString("password");

                    if (!BCrypt.checkpw(oldPassword, currentHash)) {
                        return false;
                    }

                    String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    try (PreparedStatement psUpdate = con.prepareStatement(updateQuery)) {
                        psUpdate.setString(1, newHash);
                        psUpdate.setInt(2, clientId);
                        return psUpdate.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getAvatarPathById(int clientId) {
        String sql = "SELECT avatar_path FROM client WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("avatar_path");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}