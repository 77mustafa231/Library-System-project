package com.example.library.ui;

import com.example.library.db.BookDAO;
import com.example.library.db.LoanDAO;
import com.example.library.db.MemberDAO;
import com.example.library.models.Book;
import com.example.library.models.Loan;
import com.example.library.models.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LoanPanel extends JPanel {
    private final LoanDAO loanDAO = new LoanDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final MemberDAO memberDAO = new MemberDAO();

    private final DefaultTableModel loansModel = new DefaultTableModel(new Object[]{"ID","Book ID","Member ID","Loan Date","Return Date","Returned"}, 0);
    private final JTable loansTable = new JTable(loansModel);

    private final JComboBox<String> booksBox = new JComboBox<>();
    private final JComboBox<String> membersBox = new JComboBox<>();

    public LoanPanel() {
        setLayout(new BorderLayout());

        JPanel control = new JPanel(new GridLayout(2, 4, 8, 8));
        JButton borrowBtn = new JButton("Borrow");
        JButton returnBtn = new JButton("Return Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        control.add(new JLabel("Book:")); control.add(booksBox);
        control.add(new JLabel("Member:")); control.add(membersBox);
        control.add(borrowBtn); control.add(returnBtn); control.add(deleteBtn);

        add(new JScrollPane(loansTable), BorderLayout.CENTER);
        add(control, BorderLayout.SOUTH);

        borrowBtn.addActionListener(e -> borrow());
        returnBtn.addActionListener(e -> returnSelected());
        deleteBtn.addActionListener(e -> deleteSelected());

        refresh();
    }

    private void refresh() {
        // Refresh loans table
        loansModel.setRowCount(0);
        for (Loan l : loanDAO.findAll()) {
            loansModel.addRow(new Object[]{ l.getId(), l.getBookId(), l.getMemberId(), l.getLoanDate(), l.getReturnDate(), l.isReturned() ? "Yes" : "No" });
        }

        // Refresh books and members combo
        booksBox.removeAllItems();
        List<Book> books = bookDAO.findAll();
        for (Book b : books) {
            if (b.isAvailable()) {
                booksBox.addItem(b.getId() + " - " + b.getTitle());
            }
        }

        membersBox.removeAllItems();
        for (Member m : memberDAO.findAll()) {
            membersBox.addItem(m.getId() + " - " + m.getName());
        }
    }

    private void borrow() {
        if (booksBox.getItemCount() == 0 || membersBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please ensure there is at least one available book and one member.");
            return;
        }
        int bookId = Integer.parseInt(((String) booksBox.getSelectedItem()).split(" - ")[0]);
        int memberId = Integer.parseInt(((String) membersBox.getSelectedItem()).split(" - ")[0]);
        loanDAO.borrow(bookId, memberId, LocalDate.now().toString());
        new BookDAO().setAvailability(bookId, false);
        refresh();
    }

    private void returnSelected() {
        int row = loansTable.getSelectedRow();
        if (row < 0) return;
        int loanId = (int) loansModel.getValueAt(row, 0);
        int bookId = (int) loansModel.getValueAt(row, 1);
        loanDAO.markReturned(loanId, LocalDate.now().toString());
        new BookDAO().setAvailability(bookId, true);
        refresh();
    }

    private void deleteSelected() {
        int row = loansTable.getSelectedRow();
        if (row < 0) return;
        int loanId = (int) loansModel.getValueAt(row, 0);
        loanDAO.delete(loanId);
        refresh();
    }
}
