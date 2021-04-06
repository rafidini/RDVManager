package com.example.rdvmanager;

import android.content.ContentValues;
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

    /**
     * Add a new RDV in database
     * @param pRdv the new RDV to add in the database
     */
    public void add(Rdv pRdv) {
        /* Prepare INSERT Query */
        final String vQuery =
                "INSERT INTO " + TABLE_NAME +
                        "(" + TITLE + "," + DATE + "," + TIME + "," +
                        PERSON + "," + ADDRESS + "," + PHONE_NUMBER +
                        "," + DONE + ")" +
                        " VALUES (" +
                        "'" + pRdv.getTitle() + "','" + pRdv.getDate() + "','" +
                        pRdv.getTime() + "','" + pRdv.getPerson() + "','" +
                        pRdv.getAddress() + "','" + pRdv.getPhoneNumber() + "','" +
                        (pRdv.isDone()?1:0) +
                        "');";

        /* Execute the Insert Query */
        this.database.execSQL(vQuery);
    }

    /**
     * Update a Rdv based on its id.
     * @param pRdv a Rdv with the specigic id and data
     * @return
     */
    public int update(Rdv pRdv) {
        /* Prepare data for Query */
        final Long vId = pRdv.getId();
        ContentValues vContentValues = new ContentValues();
        vContentValues.put(TITLE, pRdv.getTitle());
        vContentValues.put(DATE, pRdv.getDate());
        vContentValues.put(TIME, pRdv.getTime());
        vContentValues.put(PERSON, pRdv.getPerson());
        vContentValues.put(ADDRESS, pRdv.getAddress());
        vContentValues.put(PHONE_NUMBER, pRdv.getPhoneNumber());

        /* Execute the UPDATE Query */
        int vCount = this.database.update(TABLE_NAME, vContentValues, _ID + " = " + vId, null);

        return vCount;
    } // update(.)

    /**
     * Delete a Rdv from database given its id.
     * @param _id Rdv id
     */
    public int delete(long _id) {
        return this.database.delete(TABLE_NAME, _ID + " = " + _id, null);
    }

    /**
     * Returns a Cursor over the list of tuples in
     * table RDV.
     * @return Cursor
     */
    public Cursor getAllRdv() {
        /* Prepare columns */
        final String[] vProjection = {_ID, TITLE, DATE, TIME, PERSON, ADDRESS, PHONE_NUMBER, DONE};

        /* Query the database over the columns */
        final Cursor vCursor = this.database.query(TABLE_NAME, vProjection, null,
                null,null,null,null,null);

        return vCursor;
    }
}
