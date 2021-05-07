package com.example.mvvmrecycler.datamodel;

public class StaticDataModel {

    public void setTextTitleBtn(SetTextTitleBtn setTextTitleBtn) {

        setTextTitleBtn.number("Number");
        setTextTitleBtn.title("Title");
        setTextTitleBtn.url("Url");
        setTextTitleBtn.increase("增加");
        setTextTitleBtn.refresh("刷新");

    }

    public interface SetTextTitleBtn {

        void number(String number);

        String title(String title);

        void url(String url);

        void increase(String increase);

        void refresh(String refresh);

    }

}
