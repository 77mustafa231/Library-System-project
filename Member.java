package com.example.library.models;

public class Member extends User {
    private String membershipType;

    public Member(int id, String name, String membershipType) {
        super(id, name);
        this.membershipType = membershipType;
    }

    @Override
    public void displayInfo() {
        System.out.println("Member: " + name + " [Type: " + membershipType + "]");
    }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
}
