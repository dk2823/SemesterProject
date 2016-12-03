package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import database.DBHelper;
import database.RestaurantDBO;
import models.Restaurant;

public class OrderPickupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pickup);

        Intent thisIntent = getIntent();

        if(!thisIntent.hasExtra(DBHelper.COLUMN_RESTAURANT_ID)){
            throw new IllegalArgumentException("There's no ingredient");
        }

        long restId = thisIntent.getExtras().getLong(DBHelper.COLUMN_RESTAURANT_ID);

        RestaurantDBO restaurantDBO = new RestaurantDBO(OrderPickupActivity.this);
        Restaurant currRest = restaurantDBO.getRestaurantById(restId);

        TextView orderNumber = (TextView) findViewById(R.id.tvOrderNumber);
        TextView restaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        TextView address = (TextView) findViewById(R.id.tvAddress);

        orderNumber.setText("Order Number: " + 123456);
        restaurantName.setText(currRest.getName());
        address.setText(currRest.getAddress());

        restaurantDBO.close();
    }
}
