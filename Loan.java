package com.example.library.models;

public class Loan {
    private int id;
    private int bookId;
    private int memberId;
    private String loanDate;
    private String returnDate;
    private boolean returned;

    public Loan(int id, int bookId, int memberId, String loanDate, String returnDate, boolean returned) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    public int getId() { return id; }
    public int getBookId() { return bookId; }
    public int getMemberId() { return memberId; }
    public String getLoanDate() { return loanDate; }
    public String getReturnDate() { return returnDate; }
    public boolean isReturned() { return returned; }

    public void setId(int id) { this.id = id; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public void setLoanDate(String loanDate) { this.loanDate = loanDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public void setReturned(boolean returned) { this.returned = returned; }
}
