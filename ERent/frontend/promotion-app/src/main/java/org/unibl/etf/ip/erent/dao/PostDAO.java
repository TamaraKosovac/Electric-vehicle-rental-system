package org.unibl.etf.ip.erent.dao;

import org.unibl.etf.ip.erent.dto.PostDTO;
import org.unibl.etf.ip.erent.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public static void insert(PostDTO post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null.");
        }
        if (post.getTitle() == null || post.getTitle().trim().length() < 2 || post.getTitle().trim().length() > 100) {
            throw new IllegalArgumentException("Title must be between 2 and 100 characters.");
        }
        if (post.getContent() == null || post.getContent().trim().length() < 5 || post.getContent().trim().length() > 1000) {
            throw new IllegalArgumentException("Content must be between 5 and 1000 characters.");
        }
        if (post.getCreatedAt() == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null.");
        }

        String query = "INSERT INTO post (title, content, created_at) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, post.getTitle().trim());
            ps.setString(2, post.getContent().trim());
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
        if (keyword == null) {
            keyword = "";
        }

        String query = "SELECT * FROM post WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, "%" + keyword.trim() + "%");
            ps.setString(2, "%" + keyword.trim() + "%");

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