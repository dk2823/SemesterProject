package com.example.dk.semesterproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderPickupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pickup);

        TextView orderNumber = (TextView) findViewById(R.id.tvOrderNumber);
        TextView restaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        TextView address = (TextView) findViewById(R.id.tvAddress);

        orderNumber.setText("Order Number: " + 123456);
        restaurantName.setText("Salad Bar");
        address.setText("1234 Rockville pike, MD");
    }
}
