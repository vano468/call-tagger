package com.vano468.calltagger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBWorker {

    Context context;
    DBHelper dbHelper;

    DBWorker(Context _context) {
        context = _context;
        dbHelper = new DBHelper(context);
    }

    public void insert(String number, String tag) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        if (!checkNumberExistence(database, number)) {
            long idTag = getExistenceTagId(database, tag);
            if (idTag == 0)
                idTag = insertTag(database, tag);
            insertNumber(database, number, idTag);
        }
    }

    public String getTagByNumber(String number) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select tag from tags where id = (select tag_id from numbers where number='" + number + "')", null);
        String tag = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tag = cursor.getString(0);
        }
        cursor.close();
        return tag;
    }

    private long insertTag(SQLiteDatabase database, String tag) {
        ContentValues cv;
        cv = new ContentValues();
        cv.put("tag", tag);
        return database.insert("tags", null, cv);
    }

    private long getExistenceTagId(SQLiteDatabase database, String tag) {
        Cursor cursor = database.rawQuery("select id from tags where tag='" + tag + "'", null);
        long id = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    private void insertNumber(SQLiteDatabase database, String number, long idTag) {
        ContentValues cv;
        cv = new ContentValues();
        cv.put("number", number);
        cv.put("tag_id", idTag);
        database.insert("numbers", null, cv);
    }

    private boolean checkNumberExistence(SQLiteDatabase database, String number) {
        Cursor cursor= database.rawQuery("select count(*) from numbers where number='" + number + "'", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if (count > 0)
            return true;
        else
            return false;
    }
}
