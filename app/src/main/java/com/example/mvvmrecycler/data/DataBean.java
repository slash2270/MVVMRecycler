package com.example.mvvmrecycler.data;

public class DataBean {

    public String name, number, content;

    public DataBean(String name, String number, String content) {
        this.name = name;
        this.number = number;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
