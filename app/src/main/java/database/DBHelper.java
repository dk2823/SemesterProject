package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DK on 11/8/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // define database name and version
    private static final String DB_NAME = "saladBarDB.db";
    private static final int DB_VERSION = 28;

    // define table name and columns

    // restaurants table
    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_RESTAURANT_NAME = "name";
    public static final String COLUMN_RESTAURANT_ADDRESS = "address";

    // ingredients table
    public static final String TABLE_INGREDIENTS = "ingredients";
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_INGREDIENT_NAME = "name";
    public static final String COLUMN_INGREDIENT_COOKTIME = "cooktime";
    public static final String COLUMN_INGREDIENT_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_INGREDIENT_PRICE = "price";
    public static final String COLUMN_INGREDIENT_IMAGE_ID = "image_id";

    // preferences table
    public static final String TABLE_PREFERENCES = "preferences";
    public static final String COLUMN_PREFERENCE_ID = "preference_id";
    public static final String COLUMN_PREFERENCE_NAME = "name";
    public static final String COLUMN_PREFERENCE_LIST = "list";
    public static final String COLUMN_PREFERENCE_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_PREFERENCE_USER_ID = "user_id";
    public static final String COLUMN_PREFERENCE_QUAN_LIST = "quantity_list";

    // user table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_EMAIL = "email";

    // make create table query

    private static final String SQL_CREATE_TABLE_RESTAURANTS = "CREATE TABLE " +
            TABLE_RESTAURANTS + "("
            + COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESTAURANT_NAME + " TEXT NOT NULL, "
            + COLUMN_RESTAURANT_ADDRESS + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABLE_INGREDIENTS = "CREATE TABLE " +
            TABLE_INGREDIENTS + "("
            + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, "
            + COLUMN_INGREDIENT_COOKTIME + " INTEGER NOT NULL, "
            + COLUMN_INGREDIENT_RESTAURANT_ID + " INTEGER NOT NULL, "
            + COLUMN_INGREDIENT_PRICE + " INTEGER NOT NULL, "
            + COLUMN_INGREDIENT_IMAGE_ID + " INTEGER NOT NULL);";

    private static final String SQL_CREATE_TABLE_PREFERENCES = "CREATE TABLE " +
            TABLE_PREFERENCES + "("
            + COLUMN_PREFERENCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PREFERENCE_NAME + " TEXT NOT NULL, "
            + COLUMN_PREFERENCE_LIST + " TEXT NOT NULL, "
            + COLUMN_PREFERENCE_RESTAURANT_ID + " INTEGER NOT NULL, "
            + COLUMN_PREFERENCE_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_PREFERENCE_QUAN_LIST + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " +
            TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_USERNAME + " TEXT NOT NULL, "
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL);";

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
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        Log.w(TAG, "Upgrading the database from version " + oldV + " to " + newV);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }
}

/* This shows how to operate with Database operation for each models
                UserDBO userDBO = new UserDBO(MainActivity.this);
                RestaurantDBO restaurantDBO = new RestaurantDBO(MainActivity.this);
                IngredientDBO ingredientDBO = new IngredientDBO(MainActivity.this);
                PreferenceDBO preferenceDBO = new PreferenceDBO(MainActivity.this);

                User u1 = userDBO.createUser("DK", "DK2823", "1234", "dkyu2823@gmail.com");
                User u2 = userDBO.createUser("DK1", "DK2823", "1234", "dkyu2823@gmail.com");

                if( u2 == null ){
                    System.out.println("duplicated username");
                }

                Restaurant r = restaurantDBO.createRestaurant("restA", "Rockville pike, MD");
                Ingredient i1, i2, i3, i4, i5;
                i1 = ingredientDBO.createIngredient("mushroom", 5, r.getRestaurantId(), 1, 100);
                i2 = ingredientDBO.createIngredient("Broccoli", 3, r.getRestaurantId(), 2, 101);
                i3 = ingredientDBO.createIngredient("Celery", 2, r.getRestaurantId(), 2, 102);
                i4 = ingredientDBO.createIngredient("Arugula", 1, r.getRestaurantId(), 1, 103);
                i5 = ingredientDBO.createIngredient("Corn salad", 60, r.getRestaurantId(), 3, 104);

                String ingredientsListStr = "" + i1.getIngredientId() + ","
                        + i3.getIngredientId() + "," + i5.getIngredientId();

                String quanList = "" + i1.getIngredientId() + "=" + Preference.SM + ","
                        + i3.getIngredientId() + "=" + Preference.MD + ","
                        + i5.getIngredientId() + "=" + Preference.LG;


                preferenceDBO.createPreference("my special salad on Tuesday",
                        ingredientsListStr, r.getRestaurantId(), u1.getUserId(), quanList);


                userDBO.close();
                restaurantDBO.close();
                ingredientDBO.close();
                preferenceDBO.close();

* */
