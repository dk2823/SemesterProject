package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import models.Preference;
import models.User;

/**
 * Created by DK on 11/10/16.
 */

public class UserDBO {

    public static final String TAG = "UserDBO";

    private SQLiteDatabase mDatabase;
    private DBHelper mHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_USER_ID,
            DBHelper.COLUMN_USER_NAME, DBHelper.COLUMN_USER_USERNAME,
            DBHelper.COLUMN_USER_PASSWORD, DBHelper.COLUMN_USER_EMAIL
    };

    public UserDBO(Context context){
        this.mContext = context;
        mHelper = new DBHelper(context);

        try{
            open();
        }catch(SQLException e){
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close(){
        mDatabase.close();
    }

    public User createUser(String name, String username, String password, String email){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_NAME, name);
        values.put(DBHelper.COLUMN_USER_USERNAME, username);
        values.put(DBHelper.COLUMN_USER_PASSWORD, password);
        values.put(DBHelper.COLUMN_USER_EMAIL, email);

        long insertId = mDatabase.insert(DBHelper.TABLE_USERS, null, values);


        Cursor cursorToCreate = mDatabase.query(DBHelper.TABLE_USERS, mAllColumns, DBHelper.COLUMN_USER_ID
                + " = " + insertId, null, null, null, null);

        cursorToCreate.moveToFirst();
        User newUser = cursorToUser(cursorToCreate);
        cursorToCreate.close();
        cursorToCreate.close();

        return newUser;

    }


    public User getByUsername(String username){

        if(isExist(username)){
            Cursor cursor = mDatabase.query(DBHelper.TABLE_USERS, mAllColumns,
                    DBHelper.COLUMN_USER_USERNAME + " = ?",
                    new String[]{username}, null, null, null);

            User user = cursorToUser(cursor);
            cursor.close();

            return user;
        }

        return null;
    }

    public boolean isExist(String username){

        Cursor cursor = mDatabase.query(DBHelper.TABLE_USERS, mAllColumns,
                DBHelper.COLUMN_USER_USERNAME + " = ?",
                new String[]{username}, null, null, null);

        if(cursor.getCount() > 0){
            cursor.close();

            return true;
        }else{
            cursor.close();

            return false;
        }
    }

    protected User cursorToUser(Cursor cursor){
        User newUser = new User();
        newUser.setUserId(cursor.getLong(0));
        newUser.setName(cursor.getString(1));
        newUser.setUsername(cursor.getString(2));
        newUser.setPassword(cursor.getString(3));
        newUser.setEmail(cursor.getString(4));

        return newUser;
    }


}
