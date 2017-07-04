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
        if(!createTable){
            db.execSQL("CREATE TABLE IF NOT EXISTS \"CHECK\"(" +
                    "autologin Integer PRIMARY KEY," +
                    "first Integer" +
                    ")");
            createTable = true;
        } else if(createTable){
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

    public void Insert(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO \"CHECK\" VALUES(0, 1);");
    }

    public void AutoLogin(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE CHECK SET CHECK.autologin = 1");
    }

    public void First(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE CHECK SET CHECK.first = 0");
    }

    public boolean isAutoLogined(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select autologin from \"CHECK\"", null);

        if(cursor.getInt(0) == 1) {
            // 자동로그인 활성화
            return true;
        } else {
            return false;
        }
    }

    public boolean isFirstExecution(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select first from \"CHECK\"", null);

        if(cursor.getInt(0) == 1) {
            // 첫 실행
            return true;
        } else {
            return false;
        }
    }
}
