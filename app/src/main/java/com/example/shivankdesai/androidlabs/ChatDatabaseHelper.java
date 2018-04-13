package com.example.shivankdesai.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shivankdesai on 2018-03-01.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";


    public static final String DATABASE_NAME = "Message.db";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Message_Table";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i("ChatDatabaseHelper",
                "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, old Version=" + oldVer + " newVersion=" + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}