package com.example.mvvmrecycler.data;

public class MainBean {

    public int id;
    public String title, url, color;

    public MainBean(int id, String title, String url, String color) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.color = color;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
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
