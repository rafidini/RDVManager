package com.example.rdvmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A subclass of SQLiteOpenHelper which helps in order to do the different database operations.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    /* Table Name */
    public static final String TABLE_NAME = "RDV";

    /* Table Columns */
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String PERSON = "person";
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String DONE = "done";

    /* Database Information */
    static final String DB_NAME = "RDVManager.DB";

    /* Database Version */
    static final int DB_VERSION = 1;

    /* Constraints */
    static final String NOTNULL = " NOT NULL ";

    /* Creating Table Query */
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," +
                    TITLE + " TEXT" + NOTNULL + "," +
                    DATE + " TEXT" + NOTNULL + "," +
                    TIME + " TEXT" + NOTNULL + "," +
                    PERSON + " TEXT" + NOTNULL + "," +
                    ADDRESS + " TEXT" + NOTNULL + "," +
                    PHONE_NUMBER + " TEXT" + NOTNULL + "," +
                    DONE + "  INTEGER" + NOTNULL + ");" ;

    /**
     * Class constructor.
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Create the SQLiteDatabase database.
     * @param db the SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Upgrade the SQLiteDatabase by dropping the former version.
     * @param db         the SQLiteDatabase
     * @param oldVersion int representing the former database version
     * @param newVersion int representing the new/current database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Open the database as a writable database.
     */
    public void open() throws SQLException {
        this.database = this.getWritableDatabase();
    }

    /**
     * Close the database.
     */
    public void close() {
        this.database.close();
    }

    /* CRUD Operation methods */
    public void add() { }
    public void update() { }
    public void delete() { }
    public Cursor getAllRdv() { return null;}
}
