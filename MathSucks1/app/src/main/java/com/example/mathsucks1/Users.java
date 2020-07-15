package com.example.mathsucks1;

import java.util.ArrayList;

public class Users {
    private String userName;
    private String password;
    private String email;
    private ArrayList<Medicin> listMO;


    public void setUserName(String name) {
        this.userName = name;
    }
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String name) {
        this.password = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }



    public void setListPO(ArrayList<Medicin> listPO) {
        this.listMO.addAll(listPO);
    }

    public ArrayList<Medicin> getListPO() {
        return listMO;
    }

    public Users() {
        this.userName = "";
        this.listMO = new ArrayList<>();

    }
    public Users(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.listMO = new ArrayList<>();

    }
}
