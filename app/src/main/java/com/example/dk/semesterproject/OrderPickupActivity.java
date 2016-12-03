package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import database.DBHelper;
import database.IngredientDBO;
import database.RestaurantDBO;
import models.Ingredient;
import models.Restaurant;

public class OrderPickupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pickup);

        Intent thisIntent = getIntent();

        final String ingredientIds = thisIntent.getExtras().getString(OrderActivity.INGREDIENTS);

        String[] idArray = getIds(new String(ingredientIds));

        IngredientDBO ingredientDBO = new IngredientDBO(OrderPickupActivity.this);
        RestaurantDBO restaurantDBO = new RestaurantDBO(OrderPickupActivity.this);
        ArrayList<Ingredient> list = new ArrayList<Ingredient>();

        for(int i = 0; i < idArray.length; i++){
            list.add(ingredientDBO.getIngredientById(Integer.parseInt(idArray[i])));
        }

        Restaurant currRest = restaurantDBO.getRestaurantById(list.get(0).getRestaurantId());

        Random r = new Random();

        String orderNum = "# " + r.nextInt(99);

//        String restName = thisIntent.getExtras().getString(DBHelper.COLUMN_RESTAURANT_NAME);
//        String restAddress = thisIntent.getExtras().getString(DBHelper.COLUMN_RESTAURANT_ADDRESS);
//        String orderNum = thisIntent.getExtras().getString("orderNum");

        TextView orderNumber = (TextView) findViewById(R.id.tvOrderNumber);
        TextView restaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        TextView address = (TextView) findViewById(R.id.tvAddress);

        orderNumber.setText(orderNum);
        restaurantName.setText(currRest.getName());
        address.setText(currRest.getAddress());

        ingredientDBO.close();
        restaurantDBO.close();

    }

    private String[] getIds(String ids){
        String[] result = ids.split(",");

        return result;
    }
}
