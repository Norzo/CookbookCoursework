package com.example.cookbook.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE recipe (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "name VARCHAR(128) NOT NULL," +
                "instructions VARCHAR(128) NOT NULL," +
                "rating VARCHAR(128) NOT NULL" +
                ");");

        db.execSQL("CREATE TABLE ingredient (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "name VARCHAR(128) NOT NULL" +
                ");");

        db.execSQL("INSERT INTO recipe (name, instructions, rating) VALUES ('chicken pasta', 'cook it', '4');");
        db.execSQL("INSERT INTO ingredients (name) VALUES ('chicken');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS recipe");
        db.execSQL("DROP TABLE IF EXISTS ingredient");
        onCreate(db);
    }
}
