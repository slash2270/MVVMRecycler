package com.example.mvvmrecycler.tools;

public class Constant {

    // log
    public static final String TAG = "tag";
    public static final String MSG = "取得 + ";

    // db
    public static final String DATABASE_NAME = "rv.db";
    public static final int DATABASE_VERSION = 1;

    //  first table
    public static final String TABLE_NAME_MAIN = "main_table";
    public static final String MAIN_ID = "id";
    public static final String MAIN_NUMBER = "name";
    public static final String MAIN_TITLE = "number";
    public static final String MAIN_URL = "content";
    public static final String MAIN_COLOR = "color";

    // second table
    public static final String TABLE_NAME_RV = "rv_table";
    public static final String RV_ID = "id";

    // sql
    public static final String MAIN_COLUMNS = "(" + MAIN_ID + ", " + MAIN_NUMBER + ", " + MAIN_TITLE + ", " + MAIN_URL + ", " + MAIN_COLOR + ")";
    public static final String MAIN_QUESTIONS = "(?,?,?,?,?)";
    public static final String RV_COLUMNS = "(" + RV_ID + ")";
    public static final String RV_QUESTIONS = "(?)";

    // handler
    public static final int MESSAGE_WHAT_DATA = 1;
    public static final String MESSAGE_KEY = "key";

    // flag
    public static final String FLAG_MAIN = "MainActivity";

    // activity
    public static final String LIFE_CYCLE = "LifeCycle";

}