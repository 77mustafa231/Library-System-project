package com.example.library.ui;

import com.example.library.core.Library;
import com.example.library.db.BookDAO;
import com.example.library.models.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookPanel extends JPanel {
    private final BookDAO bookDAO = new BookDAO();
    private final Library library = new Library();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Title","Author","Available"}, 0);
    private final JTable table = new JTable(model);

    public BookPanel() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(1, 5, 8, 8));
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JButton addBtn = new JButton("Add");
        JButton delBtn = new JButton("Delete Selected");
        JButton toggleBtn = new JButton("Toggle Availability");
        form.add(new JLabel("Title:")); form.add(titleField);
        form.add(new JLabel("Author:")); form.add(authorField);
        form.add(addBtn); form.add(delBtn); form.add(toggleBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter title and author");
                return;
            }
            bookDAO.insert(title, author);
            titleField.setText(""); authorField.setText("");
            refresh();
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                bookDAO.delete(id);
                refresh();
            }
        });

        toggleBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                boolean available = "Yes".equals(model.getValueAt(row, 3));
                bookDAO.setAvailability(id, !available);
                refresh();
            }
        });

        refresh();
    }

    private void refresh() {
        List<Book> books = bookDAO.findAll();
        library.setBooks(books); // Composition + in-memory cache
        model.setRowCount(0);
        for (Book b : library.getBooksAsArray()) { // Array usage
            model.addRow(new Object[]{ b.getId(), b.getTitle(), b.getAuthor(), b.isAvailable() ? "Yes" : "No" });
        }
    }
}
