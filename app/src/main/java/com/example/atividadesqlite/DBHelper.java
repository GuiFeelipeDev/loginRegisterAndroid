package com.example.atividadesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="formula_db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override//Criação da TABLE
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GrandPrix (id INTEGER PRIMARY KEY AUTOINCREMENT,gp_name TEXT, gp_winner TEXT, leader_gap TEXT, winner_team TEXT, crash BOOLEAN, ver_win BOOLEAN, sar_point BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS GrandPrix");
    }
    //Função para inserir um dado novo na table
    public boolean insertGpData(String gp_name, String gp_winner, String leader_gap, String winner_team, Boolean crash, Boolean ver_win, Boolean sar_point) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gp_name", gp_name);
        contentValues.put("gp_winner", gp_winner);
        contentValues.put("leader_gap", leader_gap);
        contentValues.put("winner_team", winner_team);
        contentValues.put("crash", crash);
        contentValues.put("ver_win", ver_win);
        contentValues.put("sar_point", sar_point);

        long data = db.insert("GrandPrix", null, contentValues);

        return data != -1;
    }
    //Cursor para percorrer a DB
    public Cursor getGpData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM GrandPrix", null);
    }
}