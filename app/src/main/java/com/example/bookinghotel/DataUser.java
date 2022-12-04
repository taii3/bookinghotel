package com.example.bookinghotel;

import java.io.Serializable;

public class DataUser implements Serializable {
    String user;
    String pass;

    public DataUser(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
