package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import database.IngredientDBO;
import database.PreferenceDBO;
import database.RestaurantDBO;
import database.UserDBO;
import models.Ingredient;
import models.Preference;
import models.Restaurant;
import models.User;

public class MainActivity extends Activity {
    private static final int CREATE_ACCOUNT= 0;
    private static final int RECOVER_PASSWORD= 1;
    public static final String EMAIL="email";
    public static final String USERNAME="username";

    private Button mLogin;
    private Button mRegister;
    private Button mForgotPassword;
    private TextView mStatus;
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get the buttons references
        mLogin= (Button) findViewById(R.id.login);
        mRegister= (Button) findViewById(R.id.registerHelp);
        mForgotPassword= (Button) findViewById(R.id.forgotPasswordHelp);

        // Get the status textView
        mStatus= (TextView) findViewById(R.id.registrationOrpasswordRecoveryResult);

        // Get the reference to the username and password editext
        mUsername= (EditText) findViewById(R.id.username_main);
        mPassword= (EditText) findViewById(R.id.password_main);

        // Attach listeners to the buttons. When the user clicks on the registration button
        // then the RegisterActivity will start
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authenticate()) {
                    Intent intent= new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra(USERNAME, mUsername.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RegisterActivity
                Intent intent= new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, CREATE_ACCOUNT);
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), PasswordRecoveryActivity.class);
                startActivityForResult(intent, RECOVER_PASSWORD);
            }
        });

        // Initiate and populate the database
        initDB();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode==CREATE_ACCOUNT) {
                mStatus.setText(R.string.create_success);
                mUsername.setText(data.getExtras().getString(USERNAME));
            }
            else
                mStatus.setText("Password successfully sent to\n"+
                    data.getExtras().getString(EMAIL));
        }
    }

    /**
     * Authenticates the username and the password. Returns true if the username and the password
     * match a user in the database
     * @return true if the user exists false otherwise
     */
    private boolean authenticate() {
        String username= mUsername.getText().toString().trim();
        String password= mPassword.getText().toString().trim();
        boolean result;

        if (username.equals("") || password.equals("")) {
            mStatus.setText(R.string.entry_missing);
            result= false;
        } else {
            // Authenticate this user by query the database. If the user does not exist
            // set mStatus text to R.string.invalid_user and result should be false.
            // result should be true

            UserDBO userDBO = new UserDBO(MainActivity.this);

            User user = userDBO.getByUsername(username);

            if(user == null){

                mStatus.setText(R.string.invalid_user);
                result = false;
            }else{
                result = user.checkPassword(password) ? true : false;
            }

            userDBO.close();
        }

        return result;
    }

    private void initDB(){
        RestaurantDBO restaurantDBO = new RestaurantDBO(MainActivity.this);
        IngredientDBO ingredientDBO = new IngredientDBO(MainActivity.this);

        Restaurant r1 = null, r2 = null, r3 = null;

        if( !restaurantDBO.isPopulated() ){
            r1 = restaurantDBO.createRestaurant("House"
                    , "15209 N Frederick Rd, Rockville, MD 20850");
            r2 = restaurantDBO.createRestaurant("Panera Bread"
                    , "10913 Baltimore Ave, Beltsville, MD 20705");
            r3 = restaurantDBO.createRestaurant("Chart House"
                    , "12285 Tech Rd, Silver Spring, MD 20904");
        }

        if( !ingredientDBO.isPopulated() ){
            /* restaurant for House */

            ingredientDBO.createIngredient(IngredientsAdapter.BANANA_PEPPERS
                    , 50, r1.getRestaurantId(), 1.2, R.drawable.banana_peppers);

            ingredientDBO.createIngredient(IngredientsAdapter.BLACK_OLIVES
                    , 30, r1.getRestaurantId(), 1.4, R.drawable.black_olives);

            ingredientDBO.createIngredient(IngredientsAdapter.CARROT
                    , 20, r1.getRestaurantId(), 1.5, R.drawable.carrot);

            ingredientDBO.createIngredient(IngredientsAdapter.CUCUMBERS
                    , 10, r1.getRestaurantId(), 0.5, R.drawable.cucumbers);

            ingredientDBO.createIngredient(IngredientsAdapter.CROUTONS
                    , 60, r1.getRestaurantId(), 1.7, R.drawable.garlic_croutons);

            ingredientDBO.createIngredient(IngredientsAdapter.GRAPES
                    , 10, r1.getRestaurantId(), 1.4, R.drawable.grape);

            ingredientDBO.createIngredient(IngredientsAdapter.ONIONS
                    , 20, r1.getRestaurantId(), 0.7, R.drawable.onions);

            ingredientDBO.createIngredient(IngredientsAdapter.LETTUCE
                    , 5, r1.getRestaurantId(), 0.5, R.drawable.romaine_lettuce);

            ingredientDBO.createIngredient(IngredientsAdapter.CHEESE
                    , 20, r1.getRestaurantId(), 1.3, R.drawable.shredded_cheese);

            ingredientDBO.createIngredient(IngredientsAdapter.STRAWBERRY
                    , 30, r1.getRestaurantId(), 1.1, R.drawable.strawberry);

            ingredientDBO.createIngredient(IngredientsAdapter.TOMATO
                    , 10, r1.getRestaurantId(), 0.3, R.drawable.tomatoes);

            ingredientDBO.createIngredient(IngredientsAdapter.CHICKEN
                    , 80, r1.getRestaurantId(), 2.1, R.drawable.chicken);

            /* restaurant for Panera Bread */
            ingredientDBO.createIngredient(IngredientsAdapter.CUCUMBERS
                    , 30, r2.getRestaurantId(), 0.8, R.drawable.cucumbers);

            ingredientDBO.createIngredient(IngredientsAdapter.CHEESE
                    , 50, r2.getRestaurantId(), 1.8, R.drawable.shredded_cheese);

            ingredientDBO.createIngredient(IngredientsAdapter.STRAWBERRY
                    , 20, r2.getRestaurantId(), 1.3, R.drawable.strawberry);

            ingredientDBO.createIngredient(IngredientsAdapter.TOMATO
                    , 50, r2.getRestaurantId(), 0.4, R.drawable.tomatoes);

            ingredientDBO.createIngredient(IngredientsAdapter.CROUTONS
                    , 20, r2.getRestaurantId(), 1.4, R.drawable.garlic_croutons);

            ingredientDBO.createIngredient(IngredientsAdapter.GRAPES
                    , 60, r2.getRestaurantId(), 1.9, R.drawable.grape);

            ingredientDBO.createIngredient(IngredientsAdapter.ONIONS
                    , 40, r2.getRestaurantId(), 0.5, R.drawable.onions);

            ingredientDBO.createIngredient(IngredientsAdapter.LETTUCE
                    , 50, r2.getRestaurantId(), 0.7, R.drawable.romaine_lettuce);

            ingredientDBO.createIngredient(IngredientsAdapter.CARROT
                    , 40, r2.getRestaurantId(), 1.6, R.drawable.carrot);

            ingredientDBO.createIngredient(IngredientsAdapter.BANANA_PEPPERS
                    , 20, r2.getRestaurantId(), 1.5, R.drawable.banana_peppers);

            ingredientDBO.createIngredient(IngredientsAdapter.BLACK_OLIVES
                    , 10, r2.getRestaurantId(), 1.7, R.drawable.black_olives);

            ingredientDBO.createIngredient(IngredientsAdapter.CHICKEN
                    , 90, r2.getRestaurantId(), 2.7, R.drawable.chicken);

            /* restaurant for Chart House */
            ingredientDBO.createIngredient(IngredientsAdapter.LETTUCE
                    , 3, r3.getRestaurantId(), 0.7, R.drawable.romaine_lettuce);

            ingredientDBO.createIngredient(IngredientsAdapter.BANANA_PEPPERS
                    , 20, r3.getRestaurantId(), 1.0, R.drawable.banana_peppers);

            ingredientDBO.createIngredient(IngredientsAdapter.BLACK_OLIVES
                    , 10, r3.getRestaurantId(), 1.2, R.drawable.black_olives);

            ingredientDBO.createIngredient(IngredientsAdapter.CARROT
                    , 10, r3.getRestaurantId(), 1.2, R.drawable.carrot);

            ingredientDBO.createIngredient(IngredientsAdapter.CUCUMBERS
                    , 20, r3.getRestaurantId(), 0.3, R.drawable.cucumbers);

            ingredientDBO.createIngredient(IngredientsAdapter.CROUTONS
                    , 30, r3.getRestaurantId(), 1.1, R.drawable.garlic_croutons);

            ingredientDBO.createIngredient(IngredientsAdapter.GRAPES
                    , 20, r3.getRestaurantId(), 1.4, R.drawable.grape);

            ingredientDBO.createIngredient(IngredientsAdapter.ONIONS
                    , 10, r3.getRestaurantId(), 0.4, R.drawable.onions);

            ingredientDBO.createIngredient(IngredientsAdapter.CHEESE
                    , 50, r3.getRestaurantId(), 1.6, R.drawable.shredded_cheese);

            ingredientDBO.createIngredient(IngredientsAdapter.STRAWBERRY
                    , 20, r3.getRestaurantId(), 1.7, R.drawable.strawberry);

            ingredientDBO.createIngredient(IngredientsAdapter.TOMATO
                    , 10, r3.getRestaurantId(), 0.2, R.drawable.tomatoes);

            ingredientDBO.createIngredient(IngredientsAdapter.CHICKEN
                    , 30, r3.getRestaurantId(), 1.6, R.drawable.chicken);

        }


        restaurantDBO.close();
        ingredientDBO.close();


//        String ingredientsListStr = "" + i1.getIngredientId() + ","
//                + i3.getIngredientId() + "," + i5.getIngredientId();

//        String quanList = "" + i1.getIngredientId() + "=" + Preference.SM + ","
//                + i3.getIngredientId() + "=" + Preference.MD + ","
//                + i5.getIngredientId() + "=" + Preference.LG;


//        preferenceDBO.createPreference("my special salad on Tuesday",
//                ingredientsListStr, r.getRestaurantId(), u1.getUserId(), quanList);


    }

}
