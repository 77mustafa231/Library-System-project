package com.example.library.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Library Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Books", new BookPanel());
        tabs.addTab("Members", new MemberPanel());
        tabs.addTab("Loans", new LoanPanel());

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }
}
