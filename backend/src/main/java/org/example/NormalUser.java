package org.example;

public class NormalUser extends User {

    public NormalUser() {
        super();
    }

    public NormalUser(String username, String password, String phoneNumber) {
        super(username, password, phoneNumber);
    }

    @Override
    public String getRoleName() {
        return "USER";
    }
    //...
}