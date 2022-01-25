package com.example.mvvmrecycler.data;

import java.io.Serializable;
import java.util.ArrayList;

public class MainList implements Serializable {

    public ArrayList<MainBean> arrayList;

    public MainList(ArrayList<MainBean> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<MainBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MainBean> arrayList) {
        this.arrayList = arrayList;
    }

}
