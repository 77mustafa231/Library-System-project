package com.example.library.ui;

import com.example.library.core.Library;
import com.example.library.db.MemberDAO;
import com.example.library.models.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MemberPanel extends JPanel {
    private final MemberDAO dao = new MemberDAO();
    private final Library library = new Library();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name","Type"}, 0);
    private final JTable table = new JTable(model);

    public MemberPanel() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(1, 5, 8, 8));
        JTextField nameField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Standard", "Premium"});
        JButton addBtn = new JButton("Add");
        JButton delBtn = new JButton("Delete Selected");
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Type:")); form.add(typeBox);
        form.add(addBtn); form.add(delBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String type = (String) typeBox.getSelectedItem();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name");
                return;
            }
            dao.insert(name, type);
            nameField.setText("");
            refresh();
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                dao.delete(id);
                refresh();
            }
        });

        refresh();
    }

    private void refresh() {
        List<Member> members = dao.findAll();
        library.setMembers(members);
        model.setRowCount(0);
        for (Member m : library.getMembers()) {
            model.addRow(new Object[]{ m.getId(), m.getName(), m.getMembershipType() });
        }
    }
}
