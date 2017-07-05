package com.daejeonpeople.support.database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    boolean tableCreated = false;

    private volatile static DBHelper dbHelper;     //DCL사용

    public static DBHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        if (dbHelper == null){
            synchronized (DBHelper.class){
                if(dbHelper == null){
                    dbHelper = new DBHelper(context, name, factory, version);
                }
            }
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(!tableCreated){
            db.execSQL("CREATE TABLE `checker`(first INTEGER, cookie TEXT);");

            tableCreated = true;
            insert(db);
        } else if(tableCreated) {
            Log.d("Database", "Already created");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d("openDB", "db opened");
    }

    public void insert(SQLiteDatabase db){
        db.execSQL("INSERT INTO `checker`(first, cookie) VALUES(1, null);");
    }

    public boolean isFirstExecution(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT first FROM `checker`", null);
        cursor.moveToFirst();
        if(cursor.getInt(0) == 1) {
            // 첫 실행
            return true;
        } else {
            return false;
        }
    }

    public void firstExecution(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE `checker` SET first = 0");
    }

    public String getCookie(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT cookie FROM `checker`", null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public void setCookie(String cookie){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE `checker` SET cookie='" + cookie + "'");
    }
}
