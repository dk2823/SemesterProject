package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import database.DBHelper;
import database.IngredientDBO;
import database.RestaurantDBO;
import models.Ingredient;
import models.Restaurant;

public class OrderConfirmActivity extends Activity {

    private ListView mList;
    private Button mOrderBtn;
    private TextView tvRestName;
    private ImageView ivSalad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_page);

        mList = (ListView)findViewById(R.id.sampleListView);
        tvRestName = (TextView) findViewById(R.id.name_of_rest);
        ivSalad = (ImageView) findViewById(R.id.image);

        Intent thisIntent = getIntent();
        double totalPrice = 0;

        if(!thisIntent.hasExtra(OrderActivity.INGREDIENTS)){
            throw new IllegalArgumentException("There's no ingredient");
        }
        String ingredientIds = thisIntent.getExtras().getString(OrderActivity.INGREDIENTS);
        String[] idArray = getIds(ingredientIds);

        IngredientDBO ingredientDBO = new IngredientDBO(OrderConfirmActivity.this);
        RestaurantDBO restaurantDBO = new RestaurantDBO(OrderConfirmActivity.this);
        ArrayList<Ingredient> list = new ArrayList<Ingredient>();

        for(int i = 0; i < idArray.length; i++){
            list.add(ingredientDBO.getIngredientById(Integer.parseInt(idArray[i])));
        }

        for(Ingredient i : list){
            totalPrice += i.getPrice();
        }

        TextView tvTotalPrice = (TextView) findViewById(R.id.total_price);
        tvTotalPrice.setText("$" + new DecimalFormat("#.##").format(totalPrice));

        final Restaurant restaurant = restaurantDBO.getRestaurantById(list.get(0).getRestaurantId());

        tvRestName.setText(restaurant.getName());

        final ConfirmListViewAdapter adapter =
                new ConfirmListViewAdapter(OrderConfirmActivity.this, list);
        mList.setAdapter(adapter);

        mOrderBtn = (Button) findViewById(R.id.backButton);
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent= new Intent(OrderConfirmActivity.this, OrderPickupActivity.class);
                    intent.putExtra(DBHelper.COLUMN_RESTAURANT_ID
                            , adapter.getItems().get(0).getRestaurantId());
                    //intent.putExtra(USERNAME, mUsername.getText().toString().trim());
                    startActivity(intent);

            }
        });


        ingredientDBO.close();
        restaurantDBO.close();

//        String[] testStrArr = {"hello", "test", "sick", "nasty"};

//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_order_confirm_listview, testStrArr);


//        mList = (ListView) findViewById(R.id.sampleListView);
//        mList.setAdapter(adapter);
    }


    private String[] getIds(String ids){
        String[] result = ids.split(",");

        return result;
    }
}
