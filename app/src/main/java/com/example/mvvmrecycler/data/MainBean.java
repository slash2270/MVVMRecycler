package com.example.mvvmrecycler.data;

public class MainBean {

    public int id;
    public String number, title, url, color;

    public MainBean(int id, String number, String title, String url, String color) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.url = url;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
