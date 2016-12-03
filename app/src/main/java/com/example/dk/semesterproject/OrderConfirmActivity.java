package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private RelativeLayout mFrame;
    private IngredientPlacement ingredientPlacement;
    ArrayList<Ingredient> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_page);

        mList = (ListView)findViewById(R.id.sampleListView);
        tvRestName = (TextView) findViewById(R.id.name_of_rest);
        mFrame = (RelativeLayout) findViewById(R.id.image_confirmation);
        ingredientPlacement= new IngredientPlacement(OrderConfirmActivity.this);

        Intent thisIntent = getIntent();
        double totalPrice = 0;

        if(!thisIntent.hasExtra(OrderActivity.INGREDIENTS)){
            throw new IllegalArgumentException("There's no ingredient");
        }
        final String ingredientIds = thisIntent.getExtras().getString(OrderActivity.INGREDIENTS);
        String[] idArray = getIds(new String(ingredientIds));

        IngredientDBO ingredientDBO = new IngredientDBO(OrderConfirmActivity.this);
        RestaurantDBO restaurantDBO = new RestaurantDBO(OrderConfirmActivity.this);
        list = new ArrayList<Ingredient>();

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
                    Intent intent= new Intent(OrderConfirmActivity.this, PaymentActivity.class);
                    intent.putExtra(OrderActivity.INGREDIENTS, ingredientIds);
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

    private void rebuildOrderImage(){
        ingredientPlacement.setUp(mFrame);
        for(int i = 0; i < list.size(); i++){
            Ingredient ing = list.get(i);

            ingredientPlacement.addIngredient(ing.getName());

/*
            switch (ing.getName()) {
                case IngredientsAdapter.BANANA_PEPPERS:
                    ingredientPlacement.addIngredient(IngredientsAdapter.BANANA_PEPPERS);
                    break;
                case IngredientsAdapter.TOMATO:
                    ingredientPlacement.addIngredient(IngredientsAdapter.TOMATO);
                    break;
                case IngredientsAdapter.LETTUCE:
                    ingredientPlacement.addIngredient(IngredientsAdapter.LETTUCE);
                    break;
                case IngredientsAdapter.BLACK_OLIVES:
                    ingredientPlacement.addIngredient(IngredientsAdapter.BLACK_OLIVES);
                    break;
                case IngredientsAdapter.CARROT:
                    ingredientPlacement.addIngredient(IngredientsAdapter.CARROT);
                    break;
                case IngredientsAdapter.CUCUMBERS:
                    ingredientPlacement.addIngredient(IngredientsAdapter.CUCUMBERS);
                    break;
                case IngredientsAdapter.CROUTONS:
                    ingredientPlacement.addIngredient(IngredientsAdapter.CROUTONS);
                    break;
                case IngredientsAdapter.GRAPES:
                    ingredientPlacement.addIngredient(IngredientsAdapter.GRAPES);
                    break;
                case IngredientsAdapter.ONIONS:
                    ingredientPlacement.addIngredient(IngredientsAdapter.ONIONS);
                    break;
                case IngredientsAdapter.STRAWBERRY:
                    ingredientPlacement.addIngredient(IngredientsAdapter.STRAWBERRY);
                    break;
                case IngredientsAdapter.CHEESE:
                    ingredientPlacement.addIngredient(IngredientsAdapter.CHEESE);
                    break;
                default:
                    ingredientPlacement.addIngredient(IngredientsAdapter.CHICKEN);
            }
*/
        }


    }
}
