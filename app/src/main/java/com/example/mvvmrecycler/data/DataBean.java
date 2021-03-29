package com.example.mvvmrecycler.data;

public class DataBean {

    public String name, number, content, delete;

    public DataBean(String name, String number, String content, String delete) {
        this.name = name;
        this.number = number;
        this.content = content;
        this.delete = delete;
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

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

}
