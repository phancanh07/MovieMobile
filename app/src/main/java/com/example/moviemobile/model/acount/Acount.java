package com.example.moviemobile.model.acount;

public class Acount {
    private String email;
    private String number;
    private String passWord;

    public Acount() {
    }

    public Acount(String email, String number, String passWord) {
        this.email = email;
        this.number = number;
        this.passWord = passWord;
    }
    public Acount(String email, String passWord) {
        this.email = email;

        this.passWord = passWord;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Acount{" +
                "email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

}
