package com.daejeonpeople.support.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    boolean createTable = false;

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
        if(createTable == false){
            db.execSQL("CREATE TABLE LOGINCHECK(" +
                    "check boolean);");
        } else if(createTable == true){
            Log.d("Database/createTable", "Already created");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d("openDB", "db opened");
    }

    public void Insert(String title, String content, String date){
        SQLiteDatabase db = getWritableDatabase();
    }

    public String[][] getResult(){
        SQLiteDatabase db = getReadableDatabase();
    }

    public void deleteMemo(String idNum){
        SQLiteDatabase db = getWritableDatabase();
    }
}
