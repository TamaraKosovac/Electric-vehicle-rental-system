package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.PostDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public static void insert(PostDTO post) {
        String query = "INSERT INTO post (title, content, created_at) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreatedAt()));

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<PostDTO> getAll() {
        List<PostDTO> posts = new ArrayList<>();
        String query = "SELECT * FROM post ORDER BY created_at DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                posts.add(new PostDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static List<PostDTO> search(String keyword) {
        List<PostDTO> posts = new ArrayList<>();
        String query = "SELECT * FROM post WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(new PostDTO(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
}
