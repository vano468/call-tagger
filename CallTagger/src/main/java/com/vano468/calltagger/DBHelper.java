package com.vano468.calltagger;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "CallTagger", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tags ("
                + "id integer primary key autoincrement,"
                + "tag text" + ");");
        db.execSQL("create table numbers ("
                + "id integer primary key autoincrement,"
                + "number text,"
                + "tag_id integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tags");
        db.execSQL("DROP TABLE IF EXISTS numbers");
        onCreate(db);
    }
}