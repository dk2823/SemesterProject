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

        String restName = thisIntent.getExtras().getString(DBHelper.COLUMN_RESTAURANT_NAME);
        String restAddress = thisIntent.getExtras().getString(DBHelper.COLUMN_RESTAURANT_ADDRESS);
        String orderNum = thisIntent.getExtras().getString("orderNum");

        TextView orderNumber = (TextView) findViewById(R.id.tvOrderNumber);
        TextView restaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        TextView address = (TextView) findViewById(R.id.tvAddress);

        orderNumber.setText(orderNum);
        restaurantName.setText(restName);
        address.setText(restAddress);

    }
}
