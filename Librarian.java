package com.example.library.models;

public class Librarian extends User {
    public Librarian(int id, String name) {
        super(id, name);
    }

    @Override
    public void displayInfo() {
        System.out.println("Librarian: " + name + " (staff)");
    }
}
