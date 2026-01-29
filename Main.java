package com.example.library;

import com.example.library.db.Database;
import com.example.library.ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Initialize DB (creates tables if not exist)
        Database.initialize();

        // Use system look and feel for nicer UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
