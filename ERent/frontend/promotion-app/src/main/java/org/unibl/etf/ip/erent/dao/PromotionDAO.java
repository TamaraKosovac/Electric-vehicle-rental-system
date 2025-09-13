package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.PromotionDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {

    public static void insert(PromotionDTO promotion) {
        String query = "INSERT INTO promotion (title, description, start_date, end_date, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, promotion.getTitle());
            ps.setString(2, promotion.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(promotion.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(promotion.getEndDate()));
            ps.setTimestamp(5, Timestamp.valueOf(promotion.getCreatedAt()));

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<PromotionDTO> getAll() {
        List<PromotionDTO> promotions = new ArrayList<>();
        String query = "SELECT * FROM promotion ORDER BY created_at DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                promotions.add(new PromotionDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public static List<PromotionDTO> search(String keyword) {
        List<PromotionDTO> promotions = new ArrayList<>();
        String query = "SELECT * FROM promotion WHERE title LIKE ? OR description LIKE ? ORDER BY created_at DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    promotions.add(new PromotionDTO(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getTimestamp("start_date").toLocalDateTime(),
                            rs.getTimestamp("end_date").toLocalDateTime(),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }
}
