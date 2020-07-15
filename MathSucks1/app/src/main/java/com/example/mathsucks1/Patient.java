package com.example.mathsucks1;

import java.util.ArrayList;

public class Patient {
    private String name;
    private ArrayList<Medicin> listMO;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListMO(ArrayList<Medicin> listMO) {
        this.listMO = listMO;
    }

    public ArrayList<Medicin> getListMO() {
        return listMO;
    }

    public Patient() {
        this.name = "";
        this.listMO = new ArrayList<>();

    }
}
