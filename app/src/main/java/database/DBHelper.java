package database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DK on 11/8/16.
 */

//TODO
//add user table and userDBO classs
public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // define database name and version
    private static final String DB_NAME = "saladBarDB.db";
    private static final int DB_VERSION = 2;

    // define table name and columns

    // restaurants table
    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_RESTAURANT_NAME = "name";
    public static final String COULMN_RESTAURANT_ADDRESS = "address";

    // ingredients table
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_INGREDIENT_NAME = "name";
    public static final String COLUMN_INGREDIENT_COOKTIME = "cooktime";
    public static final String COLUMN_INGREDIENT_RESTAURANT_ID = "restaurant_id";

    // preferences table
    public static final String TABLE_PREFERENCES = "preferences";
    public static final String COLUMN_PREFERENCE_ID = "preference_id";
    public static final String COLUMN_PREFERENCE_NAME = "name";
    public static final String COLUMN_PREFERENCE_LIST = "list";
    public static final String COLUMN_PREFERENCE_RESTAURANT_ID = "restaurant_id";

    // make create table query

    private static final String SQL_CREATE_TABLE_RESTAURANTS = "CREATE TABLE " +
            TABLE_RESTAURANTS + "("
            + COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESTAURANT_NAME + " TEXT NOT NULL, "
            + COULMN_RESTAURANT_ADDRESS + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABLE_INGREDIENTS = "CREATE TABLE " +
            TABLE_INGREDIENTS + "("
            + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, "
            + COLUMN_INGREDIENT_COOKTIME + " INTEGER NOT NULL, "
            + COLUMN_INGREDIENT_RESTAURANT_ID + " INTEGER NOT NULL);";

    private static final String SQL_CREATE_TABLE_PREFERENCES = "CREATE TABLE " +
            TABLE_PREFERENCES + "("
            + COLUMN_PREFERENCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PREFERENCE_NAME + " TEXT NOT NULL, "
            + COLUMN_PREFERENCE_LIST + " TEXT NOT NULL, "
            + COLUMN_PREFERENCE_RESTAURANT_ID + " INTEGER NOT NULL);";

    public DBHelper(Context ctx){
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context ctx, String dbName, SQLiteDatabase.CursorFactory factory,
                    int dbVersion){
        super(ctx, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_RESTAURANTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_INGREDIENTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PREFERENCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        Log.w(TAG, "Upgrading the database from version " + oldV + " to " + newV);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);

        onCreate(db);
    }
}
