package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageDatabase extends SQLiteOpenHelper {

    private static MessageDatabase instance;

    protected final static String DATABASE_NAME = "MessageDatabase";
    protected final static int VERSION_NUM = 1;

    public final static String TABLE_NAME = "MESSAGES";

    public final static String COL_ID = "_id";
    public final static String COL_TEXT = "BODY";
    public final static String COL_TYPE = "TYPE";


    public MessageDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COL_TEXT + " text,"
        + COL_TYPE + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public static void initialize(Context ctx) {
        MessageDatabase.instance = new MessageDatabase(ctx);
    }

    public static ArrayList<Message> getAllMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        SQLiteDatabase db = MessageDatabase.instance.getReadableDatabase();
        String[] columns = { COL_ID, COL_TEXT, COL_TYPE };

        Cursor results = db.query(TABLE_NAME, columns, null, null, null, null, COL_ID);

        int idCol = results.getColumnIndex(COL_ID);
        int textCol = results.getColumnIndex(COL_TEXT);
        int typeCol = results.getColumnIndex(COL_TYPE);

        while(results.moveToNext()) {
            messages.add(new Message(
                    results.getLong(idCol),
                    results.getString(textCol),
                    MessageType.valueOf(results.getString(typeCol))
            ));
        }

        instance.printCursor(results, db.getVersion());

        return messages;
    }

    public static void addMessage(String text, MessageType type) {
        SQLiteDatabase db = MessageDatabase.instance.getWritableDatabase();

        ContentValues rowValues = new ContentValues();
        rowValues.put(COL_TEXT, text);
        rowValues.put(COL_TYPE, type.toString());

        db.insert(TABLE_NAME, null, rowValues);
    }

    public static void deleteMessage(long id) {
        SQLiteDatabase db = MessageDatabase.instance.getWritableDatabase();

        db.delete(TABLE_NAME, COL_ID + "= ?", new String[] {Long.toString(id)});
    }

    private void printCursor(Cursor c, int version) {
        Log.i(this.getClass().toString(), "DB version: " + version);
        Log.i(this.getClass().toString(), "Number of columns: " + c.getColumnCount());
        Log.i(this.getClass().toString(), "Columns: " + TextUtils.join(", ", c.getColumnNames()));
        Log.i(this.getClass().toString(), "Number of rows: " + c.getCount());

        int idCol = c.getColumnIndex(COL_ID);
        int textCol = c.getColumnIndex(COL_TEXT);
        int typeCol = c.getColumnIndex(COL_TYPE);

        c.moveToFirst();
        while(c.moveToNext()) {
            String row = "";
            for(int i = 0; i< c.getColumnCount(); i++ ) {
                row += c.getString(i);
                row += ", ";
            }
            Log.i(this.getClass().toString(), "Row: " + row);
        }
    }
}
