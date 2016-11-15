package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import models.Ingredient;

/**
 * Created by DK on 11/8/16.
 */

public class IngredientDBO {

    public static final String TAG = "Ingredient";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_INGREDIENT_ID,
            DBHelper.COLUMN_INGREDIENT_NAME, DBHelper.COLUMN_INGREDIENT_COOKTIME,
            DBHelper.COLUMN_INGREDIENT_RESTAURANT_ID, DBHelper.COLUMN_INGREDIENT_PRICE,
            DBHelper.COLUMN_INGREDIENT_IMAGE_ID };

    public IngredientDBO(Context context){
        this.mContext = context;
        this.mHelper = new DBHelper(context);

        try{
            open();
        }catch (SQLException e){
            Log.e(TAG, "SQL Exception on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close(){
        mDatabase.close();
    }

    public Ingredient createIngredient(String name, int cookTime, long restaurantId, int price, int imageId){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_INGREDIENT_NAME, name);
        values.put(DBHelper.COLUMN_INGREDIENT_COOKTIME, cookTime);
        values.put(DBHelper.COLUMN_INGREDIENT_RESTAURANT_ID, restaurantId);
        values.put(DBHelper.COLUMN_INGREDIENT_PRICE, price);
        values.put(DBHelper.COLUMN_INGREDIENT_IMAGE_ID, imageId);

        long insertId = mDatabase.insert(DBHelper.TABLE_INGREDIENTS, null, values);

        Cursor cursor = mDatabase.query(DBHelper.TABLE_INGREDIENTS, mAllColumns,
                DBHelper.COLUMN_INGREDIENT_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Ingredient newOne = cursorToIngredient(cursor);
        cursor.close();

        return newOne;
    }

    public void deleteIngredient(Ingredient deleteOne){
        long id = deleteOne.getIngredientId();

        mDatabase.delete(DBHelper.TABLE_INGREDIENTS
                , DBHelper.COLUMN_INGREDIENT_ID + " = " + id, null);
    }

    public ArrayList<Ingredient> getIngredientsListByRestaurant(long restaurantId){
        ArrayList<Ingredient> list = new ArrayList<Ingredient>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_INGREDIENTS, mAllColumns,
                DBHelper.COLUMN_INGREDIENT_RESTAURANT_ID + " = ?",
                new String[]{String.valueOf(restaurantId)}, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Ingredient each = cursorToIngredient(cursor);
            list.add(each);
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public Ingredient getIngredientById(long id){
        Cursor cursor = mDatabase.query(DBHelper.TABLE_INGREDIENTS, mAllColumns,
                DBHelper.COLUMN_INGREDIENT_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Ingredient each = cursorToIngredient(cursor);

        return each;
    }

    protected Ingredient cursorToIngredient(Cursor cursor){
        Ingredient newOne = new Ingredient();
        newOne.setIngredientId(cursor.getLong(0));
        newOne.setName(cursor.getString(1));
        newOne.setCookTime(cursor.getInt(2));
        newOne.setRestaurantId(cursor.getLong(3));
        newOne.setPrice(cursor.getInt(4));
        newOne.setImageId(cursor.getInt(5));

        return newOne;
    }
}
