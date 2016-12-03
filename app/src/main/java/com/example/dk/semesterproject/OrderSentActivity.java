package com.example.dk.semesterproject;

/**
 * Created by Alex on 11/26/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import database.DBHelper;
import database.IngredientDBO;
import database.RestaurantDBO;
import models.Ingredient;
import models.Restaurant;

public class OrderSentActivity extends Activity {
    private SeekBar mSeekBar;

    private Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_sent_page);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar4);
        //  access database ingredient time, and restaurant NAME and Address
        i = getIntent();
        final String ingredientIds = i.getExtras().getString(OrderActivity.INGREDIENTS);
        IngredientDBO ingredientDBO = new IngredientDBO(OrderSentActivity.this);
        RestaurantDBO restaurantDBO = new RestaurantDBO(OrderSentActivity.this);
        String[] ids = getIds(new String(ingredientIds));
        ArrayList<Ingredient> ing = new ArrayList<Ingredient>();

        for(int i = 0; i < ids.length; i++){
            ing.add(ingredientDBO.getIngredientById(Integer.parseInt(ids[i])));
        }

//        List<Ingredient> ing = (ArrayList<Ingredient>) intent.getSerializableExtra("ingredient");
//        Restaurant rest = (Restaurant) intent.getSerializableExtra("restaurant");
        //RestaurantDBO rest = new RestaurantDBO();
        //IngredientDBO ing = new IngredientDBO();
        //getRestaurantById();
        //getIngredientById();
        Restaurant rest = restaurantDBO.getRestaurantById(ing.get(0).getRestaurantId());

        ingredientDBO.close();
        restaurantDBO.close();

        int time = 0;
        for (int i = 0; i < ing.size(); i++) {
            time += ing.get(i).getCookTime();
        }
        Log.i("time tag", "" + time);
//        Random r = new Random();
//        int i1 = r.nextInt(99);
//        String s1 = "Order # " + i1 ;

        ArrayList<String> s = new ArrayList<String>();
       // s.add(s1);
        s.add(rest.getName());
        s.add(rest.getAddress());
        s.add("Total Time: " + time);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_order_sent_listview, s);
        ListView listView = (ListView) findViewById(R.id.sampleListView);
        listView.setAdapter(adapter);
        //Long t = time*1000l;
        new OrderSentBackground().execute(time*1000);
        //startSeek(time);

//        Intent i = new Intent(this, OrderPickupActivity.class);
//        i.putExtra("orderNum", s1);
//        i.putExtra(DBHelper.COLUMN_RESTAURANT_ADDRESS, rest.getAddress());
//        i.putExtra(DBHelper.COLUMN_RESTAURANT_NAME, rest.getName());
//        startActivity(i);
    }

//    private void startSeek(Long t) {
//        mSeekBar.setMax(t);
//        mSeekBar.setProgress(0);
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                int p = mSeekBar.getProgress();
//                p += 1000;
//                mSeekBar.setProgress(p);
//            }
//        }, 1, 1000);
//    }

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
           // super.onProgressUpdate(values[0]);
            mSeekBar.setProgress(values[0]);
        }

        protected void onPostExecute(Void result) {
            i.setClass(OrderSentActivity.this, OrderPickupActivity.class);
            startActivity(i);
        }
    }
}
