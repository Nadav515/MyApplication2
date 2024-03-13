package com.example.myapplication;// DBHelper.java
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//This is a helper class that extends SQLiteOpenHelper.
// It manages the database creation and version management.
// This class contains methods to open, create, update, and query the database.
// It is used to interact with the SQLite database for storing,
// retrieving, and managing user and water intake data.
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WaterTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_INTAKES = "water_intakes";

    // Common column names
    private static final String KEY_ID = "id";

    // USERS Table - column names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AGE = "age";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_HEIGHT = "height";

    // INTAKES Table - column names
    private static final String KEY_DATE = "date";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_USER_ID = "user_id";

    // Table Create Statements
    // Users table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USERNAME
            + " TEXT," + KEY_AGE + " INTEGER," + KEY_WEIGHT + " REAL,"
            + KEY_HEIGHT + " REAL" + ")";

    // Water Intakes table create statement
    private static final String CREATE_TABLE_INTAKES = "CREATE TABLE "
            + TABLE_INTAKES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
            + " TEXT," + KEY_AMOUNT + " REAL," + KEY_USER_ID + " INTEGER,"
            + " FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "))";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_INTAKES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTAKES);
        // create new tables
        onCreate(db);
    }

    // Adding new user
    public long addUser(String username, int age, double weight, double height) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_AGE, age);
        values.put(KEY_WEIGHT, weight);
        values.put(KEY_HEIGHT, height);

        // insert row
        long user_id = db.insert(TABLE_USERS, null, values);

        return user_id;
    }

    // Adding new water intake
    public long addIntake(String date, double amount, long user_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_AMOUNT, amount);
        values.put(KEY_USER_ID, user_id);

        // insert row
        long intake_id = db.insert(TABLE_INTAKES, null, values);

        return intake_id;
    }
    // Note: Don't forget to close database connection db.close();
}
