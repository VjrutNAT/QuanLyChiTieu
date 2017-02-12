package com.Demo.vjrutnat.quanlyschitieu.SQLiteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by VjrutNAT on 2/12/2017.
 */

public class SQLiteDataBase extends SQLiteOpenHelper {

    public static final String DB_NAME = "QuanLyChiTieu.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME_ACCOUNT = "tbl_account";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACCOUNT = "account";
    public static final String COLUMN_MONEY_SUM = "money_sum";

    public static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_NAME_ACCOUNT + "("
            + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ACCOUNT + " TEXT NOT NULL, "
            + COLUMN_MONEY_SUM + " INTEGER NOT NULL );";

    public static final String TABLE_NAME_TRANSACTION = "tbl_transaction";
    public static final String COLUMN_TRANSACTION_ID = "_id";
    public static final String COLUMN_TRANSACTION_TIME = "time";
    public static final String COLUMN_TRANSACTION_DATE = "date";
    public static final String COLUMN_TRANSACTION_CONTENT = "content";
    public static final String COLUMN_TRANSACTION_MONEY = "money";
    public static final String COLUMN_TRANSACTION_SURPLUS = "surplus";
    public static final String COLUMN_TRANSACTION_TYPE = "type_transaction";
    public static final String COLUMN_TRANSACTION_ID_ACCOUNT = "id_account";

    public static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_NAME_TRANSACTION + "("
            + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TRANSACTION_DATE + " TEXT NOT NULL, " + COLUMN_TRANSACTION_TIME + " TEXT NOT NULL, "
            + COLUMN_TRANSACTION_CONTENT + " TEXT NOT NULL, "
            + COLUMN_TRANSACTION_MONEY + " INTEGER NOT NULL, "
            + COLUMN_TRANSACTION_SURPLUS + " INTEGER NOT NULL, "
            + COLUMN_TRANSACTION_TYPE + " TEXT NOT NULL, "
            + COLUMN_TRANSACTION_ID_ACCOUNT + " TEXT NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_TRANSACTION_ID_ACCOUNT  + ") REFERENCES " + TABLE_NAME_ACCOUNT + "( "
            + COLUMN_ID + ") ON DElETE CASCADE );";


    public SQLiteDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_TRANSACTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRANSACTION);
        onCreate(db);
    }
    public Cursor query(String sql){
        return getReadableDatabase().rawQuery( sql, null);
    }

    public long insertRecordAccount(ContentValues values){
        return getWritableDatabase().insert(TABLE_NAME_ACCOUNT, null, values);
    }

    public int updateRecord(ContentValues values, String [] id){
        return getWritableDatabase().update(TABLE_NAME_ACCOUNT, values, COLUMN_ID + " =? ", id );
    }
    public void deleteRecord(String [] id){
        getWritableDatabase().delete(TABLE_NAME_ACCOUNT, COLUMN_ID + " =? ", id);
    }
    public  long insertRecordTransaction(ContentValues values){
        return getWritableDatabase().insert(TABLE_NAME_TRANSACTION, null, values);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}