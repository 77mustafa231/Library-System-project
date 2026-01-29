package com.example.library.db;

import com.example.library.models.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public List<Loan> findAll() {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT id, book_id, member_id, loan_date, return_date, returned FROM loans ORDER BY id DESC";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Loan(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    rs.getString("loan_date"),
                    rs.getString("return_date"),
                    rs.getInt("returned") == 1
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void borrow(int bookId, int memberId, String loanDate) {
        String sql = "INSERT INTO loans(book_id, member_id, loan_date, returned) VALUES(?, ?, ?, 0)";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.setInt(2, memberId);
            ps.setString(3, loanDate);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void markReturned(int loanId, String returnDate) {
        String sql = "UPDATE loans SET returned = 1, return_date = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, returnDate);
            ps.setInt(2, loanId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM loans WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
