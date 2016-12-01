package com.example.dk.semesterproject;

/**
 * Created by Alex on 11/26/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.Ingredient;
import models.Restaurant;

public class OrderSentActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_sent_page);
        //TODO  access database ingredient time, and restaurant NAME and Address
        Intent intent = getIntent();
        List<Ingredient> ing = (ArrayList<Ingredient>) intent.getSerializableExtra("ingredient");
        Restaurant rest = (Restaurant) intent.getSerializableExtra("restaurant");
        //RestaurantDBO rest = new RestaurantDBO();
        //IngredientDBO ing = new IngredientDBO();
        //getRestaurantById();
        //getIngredientById();
        int time = 0;
        for (int i = 0; i < ing.size(); i++) {
            time += ing.get(i).getCookTime();
        }
        Random r = new Random();
        int i1 = r.nextInt(99);
        String s1 = "Order # " + i1 ;



        ArrayList<String> s = new ArrayList<String>();
        s.add(s1);
        s.add(rest.getName());
        s.add(rest.getAddress());
        s.add("Total Time: " + time);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_order_sent_listview, s);
        ListView listView = (ListView) findViewById(R.id.sampleListView);
        listView.setAdapter(adapter);
    }
}
