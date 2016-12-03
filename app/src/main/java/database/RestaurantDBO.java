package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import models.Ingredient;
import models.Restaurant;

/**
 * Created by DK on 11/8/16.
 */
public class RestaurantDBO {

    public static final String TAG = "RestaurantDBO";

    private SQLiteDatabase mDatabase;
    private DBHelper mHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_RESTAURANT_ID,
            DBHelper.COLUMN_RESTAURANT_NAME, DBHelper.COLUMN_RESTAURANT_ADDRESS };

    public RestaurantDBO(Context context){
        this.mContext = context;
        mHelper = new DBHelper(context);

        try{
            open();
        } catch(SQLException e){
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close(){
        mHelper.close();
    }

    public Restaurant createRestaurant(String name, String address){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PREFERENCE_NAME, name);
        values.put(DBHelper.COLUMN_RESTAURANT_ADDRESS, address);

        long insertId = mDatabase.insert(DBHelper.TABLE_RESTAURANTS, null, values);

        Cursor cursor = mDatabase.query(DBHelper.TABLE_RESTAURANTS,
                mAllColumns, DBHelper.COLUMN_RESTAURANT_ID + " = " + insertId
                , null, null, null, null);

        cursor.moveToFirst();
        Restaurant newOne = cursorToRestaurant(cursor);
        cursor.close();

        return newOne;
    }

    public void deleteRestaurant(Restaurant restaurant){
        long id = restaurant.getRestaurantId();

        IngredientDBO ingredientDBO = new IngredientDBO(mContext);
        ArrayList<Ingredient> ingredientsList = ingredientDBO.getIngredientsListByRestaurant(id);

        if(ingredientsList != null){
            for(Ingredient i : ingredientsList){
                ingredientDBO.deleteIngredient(i);
            }
        }

        mDatabase.delete(DBHelper.TABLE_RESTAURANTS,
                DBHelper.COLUMN_RESTAURANT_ID + " = " + id, null);

        ingredientDBO.close();
    }

    public ArrayList<Restaurant> getAllRestaurants(){
        ArrayList<Restaurant> list = new ArrayList<Restaurant>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_RESTAURANTS,
                mAllColumns, null, null, null, null, null);
        if( cursor != null ){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Restaurant each = cursorToRestaurant(cursor);
                list.add(each);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return list;
    }

    public Restaurant getRestaurantById(long id){
        Cursor cursor = mDatabase.query(DBHelper.TABLE_RESTAURANTS, mAllColumns,
                DBHelper.COLUMN_RESTAURANT_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if( cursor != null ){
            cursor.moveToFirst();
        }

        return cursorToRestaurant(cursor);
    }

    protected  Restaurant cursorToRestaurant(Cursor cursor){
        Restaurant newOne = new Restaurant();
        newOne.setRestaurantId(cursor.getLong(0));
        newOne.setName(cursor.getString(1));
        newOne.setAddress(cursor.getString(2));

        return newOne;
    }

    public boolean isPopulated(){
        Cursor c = mDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_RESTAURANTS, null);
        boolean result = false;
        if( c.getCount() > 0 ){
            result = true;
        }else{
            result = false;
        }
        c.close();

        return result;
    }
}
