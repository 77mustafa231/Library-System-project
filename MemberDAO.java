package com.example.library.db;

import com.example.library.models.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public List<Member> findAll() {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT id, name, membershipType FROM members ORDER BY id DESC";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("membershipType")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void insert(String name, String membershipType) {
        String sql = "INSERT INTO members(name, membershipType) VALUES(?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, membershipType);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM members WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
