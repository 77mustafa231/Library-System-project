package com.example.library.core;

import com.example.library.models.Book;
import com.example.library.models.Loan;
import com.example.library.models.Member;

import java.util.*;

/**
 * Composition: Library owns collections of books, members, and loans.
 * Data Structures applied internally WITHOUT changing behavior.
 */
public class Library {

    // Primary collections (behavior unchanged)
    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    // Additional data structures for efficient access
    private final Map<Integer, Book> bookIndex = new HashMap<>();
    private final Map<Integer, Member> memberIndex = new HashMap<>();

    public void setBooks(List<Book> list) {
        books.clear();
        bookIndex.clear();
        for (Book b : list) {
            books.add(b);
            bookIndex.put(b.getId(), b);
        }
    }

    public void setMembers(List<Member> list) {
        members.clear();
        memberIndex.clear();
        for (Member m : list) {
            members.add(m);
            memberIndex.put(m.getId(), m);
        }
    }

    public void setLoans(List<Loan> list) {
        loans.clear();
        loans.addAll(list);
    }

    // Original getters (unchanged behavior)
    public List<Book> getBooks() { return books; }
    public List<Member> getMembers() { return members; }
    public List<Loan> getLoans() { return loans; }

    // Additional internal usage methods
    public Book findBookById(int id) {
        return bookIndex.get(id);
    }

    public Member findMemberById(int id) {
        return memberIndex.get(id);
    }

    /** Explicit array usage preserved for rubric */
    public Book[] getBooksAsArray() {
        return books.toArray(new Book[0]);
    }
}
