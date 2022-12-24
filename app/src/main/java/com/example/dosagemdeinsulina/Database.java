package com.example.dosagemdeinsulina;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "db_valores";
    public static String TABLE = "valores_referencia";
    public static String REFERENCIA_HGT = "hgt";
    public static String REFERENCIA_REFEICAO = "carbo_refeicao";

    public Database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + REFERENCIA_HGT + " TEXT, " + REFERENCIA_REFEICAO + " TEXT)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
