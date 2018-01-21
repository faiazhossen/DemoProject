package com.sabbir.admin.demoproject.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sabbir.admin.demoproject.model.UserData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Admin on 1/21/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "USERDB";


    // Contacts table name
    private static final String TABLE_NAME = "UserTB";

    // Contacts Table Columns names
    private static final String KEY_NAME = "USER_NAME";
    private static final String KEY_NUMBER = "USER_NUMBER";
    private static final String KEY_AGE = "USER_AGE";
    private static final String KEY_EMAIL= "USER_EMAIL";
    private static final String KEY_IMAGE= "USER_IMAGE";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_NAME + " TEXT,"
                + KEY_NUMBER + " TEXT,"
                + KEY_EMAIL + " TEXT PRIMARY KEY,"
                + KEY_AGE + " TEXT,"
                + KEY_IMAGE + " BLOB,"
                + "unique (" + KEY_EMAIL + ")"
                + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }







    // Adding new callData
    public void addUserData(UserData userData) {
        byte[] data = getBitmapAsByteArray(userData.getUserImg());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mValues = new ContentValues();
        mValues.put(KEY_NAME, userData.getUserName());
        mValues.put(KEY_NUMBER,userData.getUserPhn());
        mValues.put(KEY_EMAIL, userData.getUserEmail());
        mValues.put(KEY_AGE, String.valueOf(userData.getUserAge()));
        mValues.put(KEY_IMAGE,data);
        db.insertWithOnConflict(TABLE_NAME, null, mValues, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }



    // Getting All callData
    public ArrayList<UserData> getAllUserData() {
        ArrayList<UserData> mcUserDataList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(4);

                UserData mcallData = new UserData(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        BitmapFactory.decodeByteArray(img, 0, img.length));
                mcUserDataList.add(mcallData);
            } while (cursor.moveToNext());
        }
        return mcUserDataList;
    }

    // Getting single callData
    public ArrayList<UserData> getcallDataByAge() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserData> mUserDataList = new ArrayList<>();


        Cursor cursor = db.query(TABLE_NAME, new String[]{ KEY_NAME,KEY_NUMBER, KEY_EMAIL,KEY_AGE,KEY_IMAGE},
                null, null, null, null, KEY_AGE);

        if (cursor.moveToFirst()) {
            do {
                byte[] img = cursor.getBlob(4);

                UserData mcallData = new UserData(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        BitmapFactory.decodeByteArray(img, 0, img.length));
                mUserDataList.add(mcallData);
            } while (cursor.moveToNext());
        }
        return mUserDataList;
    }



    // Getting callData Count
    public int getcallDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    // Deleting single callData
    public void deletecallData(UserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + "=?", new String[]{userData.getUserName()});
        db.close();
    }

    public void deleteAllcallData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }
}