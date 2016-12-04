package com.example.dk.semesterproject;

/**
 * Created by Alex on 11/26/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.SeekBar;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;
import database.IngredientDBO;
import database.RestaurantDBO;
import models.Ingredient;
import models.Restaurant;

public class OrderSentActivity extends Activity {
    private SeekBar mSeekBar;
    private TransitionDrawable mTransitionDrawable;
    private Intent i;
    boolean allowed= false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_sent_page);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar4);

        i = getIntent();
        final String ingredientIds = i.getExtras().getString(OrderActivity.INGREDIENTS);
        IngredientDBO ingredientDBO = new IngredientDBO(OrderSentActivity.this);
        RestaurantDBO restaurantDBO = new RestaurantDBO(OrderSentActivity.this);
        String[] ids = getIds(new String(ingredientIds));
        ArrayList<Ingredient> ing = new ArrayList<Ingredient>();

        for(int i = 0; i < ids.length; i++){
            ing.add(ingredientDBO.getIngredientById(Integer.parseInt(ids[i])));
        }



        Restaurant rest = restaurantDBO.getRestaurantById(ing.get(0).getRestaurantId());

        ingredientDBO.close();
        restaurantDBO.close();

        int time = 0;
        for (int i = 0; i < ing.size(); i++) {
            time += ing.get(i).getCookTime();
        }
        Log.i("time tag", "" + time);

        mTransitionDrawable= (TransitionDrawable) getResources()
                .getDrawable(R.drawable.group_pic, null);
        mTransitionDrawable.setCrossFadeEnabled(true);
        ((ImageView) findViewById(R.id.imageView)).setImageDrawable(mTransitionDrawable);
        mTransitionDrawable.startTransition(time*1000);
        mSeekBar.setMax(1000*time);

        ArrayList<String> s = new ArrayList<String>();
       // s.add(s1);
        s.add(rest.getName());
        s.add(rest.getAddress());
        s.add("Total Time: " + time);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_order_sent_listview, s);
        ListView listView = (ListView) findViewById(R.id.sampleListView);
        listView.setAdapter(adapter);
        new OrderSentBackground().execute(time*1000);
    }


    private String[] getIds(String ids){
        String[] result = ids.split(",");

        return result;
    }


    private class OrderSentBackground extends AsyncTask<Integer, Integer, Void> {

        protected Void doInBackground(Integer... params) {
            for (int i = 0; i < params[0]; i+=1000) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i);


                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return null;
        }
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected void onProgressUpdate(Integer... values){
            mSeekBar.setProgress(values[0]);
        }

        protected void onPostExecute(Void result) {
            allowed= true;
            i.setClass(OrderSentActivity.this, OrderPickupActivity.class);
            startActivity(i);
        }
    }


    @Override
    public void onBackPressed() {
        if (!allowed)
            Toast.makeText(this, "Please be patient till your order preparation is completed...",
                    Toast.LENGTH_LONG).show();
        else
            super.onBackPressed();
    }
}
