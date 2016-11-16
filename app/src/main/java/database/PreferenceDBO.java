package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import models.Ingredient;
import models.Preference;



/**
 * Created by DK on 11/8/16.
 */


public class PreferenceDBO {
    public static final String TAG = "PreferenceDBO";

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_PREFERENCE_ID,
            DBHelper.COLUMN_PREFERENCE_NAME, DBHelper.COLUMN_PREFERENCE_LIST,
            DBHelper.COLUMN_PREFERENCE_RESTAURANT_ID, DBHelper.COLUMN_PREFERENCE_USER_ID
            , DBHelper.COLUMN_PREFERENCE_QUAN_LIST};

    public PreferenceDBO(Context context){
        this.mContext = context;
        mHelper = new DBHelper(context);

        try{
            open();
        }catch(SQLException e){
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


    // for list the String format, it should be 1,2,4,7
    // for quantityList String foramt, it should be 1=5, 2=2, 4=1, 7=2
    public Preference createPreference(String name, String list
            , long restaurantId, long userId, String quantityList){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PREFERENCE_NAME, name);
        values.put(DBHelper.COLUMN_PREFERENCE_LIST, list);
        values.put(DBHelper.COLUMN_PREFERENCE_RESTAURANT_ID, restaurantId);
        values.put(DBHelper.COLUMN_PREFERENCE_USER_ID, userId);
        values.put(DBHelper.COLUMN_PREFERENCE_QUAN_LIST, quantityList);

        long insertId = mDatabase.insert(DBHelper.TABLE_PREFERENCES, null, values);

        Cursor cursor = mDatabase.query(DBHelper.TABLE_PREFERENCES, mAllColumns,
                DBHelper.COLUMN_PREFERENCE_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Preference newOne = cursorToPreference(cursor);
        cursor.close();

        return newOne;
    }

    public void deletePreference(Preference deleteOne){
        long id = deleteOne.getPreferenceId();

        mDatabase.delete(DBHelper.TABLE_PREFERENCES,
                DBHelper.COLUMN_PREFERENCE_ID + " = " + id, null);
    }

    public ArrayList<Preference> getAllPreferences(){
        ArrayList<Preference> list = new ArrayList<Preference>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_PREFERENCES, mAllColumns,
                null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Preference each = cursorToPreference(cursor);
            list.add(each);
            cursor.moveToNext();
        }

        cursor.close();

        return list;
    }

    public Preference getPreferenceById(long id){
        Cursor cursor = mDatabase.query(DBHelper.TABLE_PREFERENCES, mAllColumns,
                DBHelper.COLUMN_PREFERENCE_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Preference each = cursorToPreference(cursor);

        return each;
    }

    protected Preference cursorToPreference(Cursor cursor){
        Preference newOne = new Preference();
        newOne.setPreferenceId(cursor.getLong(0));
        newOne.setName(cursor.getString(1));
        String[] ingredientIds = cursor.getString(2).split(",");

        IngredientDBO ingredientDBO = new IngredientDBO(mContext);
        ArrayList<Ingredient> list = new ArrayList<Ingredient>();

        for(int i = 0; i < ingredientIds.length; i++){
            list.add(ingredientDBO.getIngredientById(Long.parseLong(ingredientIds[i])));
        }

        newOne.setList(list);
        newOne.setRestaurantId(cursor.getLong(3));
        newOne.setUserId(cursor.getLong(4));

        String[] quantityList = cursor.getString(5).split(",");
        HashMap<Integer, String> quanMap = new HashMap<Integer, String>();

        for(int i = 0; i < quantityList.length; i++){
            String[] each = quantityList[i].split("=");
            quanMap.put(Integer.parseInt(each[0]), each[1]);
        }

        newOne.setQuantity(quanMap);

        return newOne;
    }

}
