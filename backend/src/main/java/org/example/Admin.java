package org.example;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String username, String password, String phoneNumber) {
        super(username, password, phoneNumber);
    }

    @Override
    public String getRoleName() {
        return "ADMIN";
    }

    //..
}