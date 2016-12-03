package com.example.dk.semesterproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import database.IngredientDBO;
import database.RestaurantDBO;
import models.Ingredient;
import models.Restaurant;

/**
 * Created by Eck on 11/20/16.
 */

public class OrderActivity extends Activity {
    public static final String USERNAME= "username";
    public static final String RESTAURANT= "restaurant";
    public static final String INGREDIENTS= "Ingredients";

    private static final String TAG= "OrderActivity";

    private TextView mUsername;
    private ViewPager mViewPager;
    private IngredientsAdapter mIngredientsAdapter;
    private ListviewAdapter mListviewAdapter;
    private ListView mListView;
    private Spinner mSpinner;
    private RelativeLayout mFrame;
    private ArrayAdapter<String> mSpinnerAdapter;
    private AlertDialog mAlertDialog;
    private Button mPlaceOrderBtn;
    //private RadioButton mSmallRadioButton;
    //private RadioButton mMediumRadioButton;
    //private RadioButton mLargeRadioButton;

    /* List of Restaurant Objects */
    private RestaurantDBO restaurantDBO;
    private IngredientDBO ingredientDBO;
    private ArrayList<Ingredient> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        mListView= (ListView) findViewById(R.id.ingredients_list);
        mUsername= (TextView) findViewById(R.id.username_order);
        mViewPager= (ViewPager) findViewById(R.id.pager_view);
        mSpinner= (Spinner) findViewById(R.id.restaurant_chooser);
        mFrame= (RelativeLayout) findViewById(R.id.salad_order);
        //mSmallRadioButton= (RadioButton) findViewById(R.id.radio_small);
        //mMediumRadioButton= (RadioButton) findViewById(R.id.radio_medium);
        //mLargeRadioButton= (RadioButton) findViewById(R.id.radio_large);

        /* Get the Restaurants Objects List from Database */
        restaurantDBO = new RestaurantDBO(OrderActivity.this);
        ingredientDBO = new IngredientDBO(OrderActivity.this);
        final ArrayList<Restaurant> restList = restaurantDBO.getAllRestaurants();
        // The spinner should display restList.get(0).getName() by default,
        // so assign ingredientsList with restList.get(0) ingredients
        ingredientsList =
                ingredientDBO.getIngredientsListByRestaurant(restList.get(0).getRestaurantId());

        // Get the names of restaurants
        String[] nameList = getRestNames(restList);

        // Retrieve the username from the intent and set the username
        String username= getIntent().getExtras().getString(MainActivity.USERNAME);
        mUsername.setText(username);

        final Intent intent= new Intent(this, OrderConfirmActivity.class);
        intent.putExtra(USERNAME, username);

        mListviewAdapter= new ListviewAdapter(getApplicationContext());
//        mSpinnerAdapter= ArrayAdapter.createFromResource(getApplicationContext(),
//                R.array.restaurants, R.layout.spinner_item);
        mSpinnerAdapter =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, nameList);
        mSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);

        mSpinner.setAdapter(mSpinnerAdapter);
        mListView.setAdapter(mListviewAdapter);
        mIngredientsAdapter= new IngredientsAdapter(this, mListviewAdapter, mFrame, ingredientsList);
        mViewPager.setAdapter(mIngredientsAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                IngredientDBO ingredientDBOtemp =
                        new IngredientDBO(OrderActivity.this.getApplicationContext());

                ArrayList<Ingredient> changedList =
                        ingredientDBOtemp.getIngredientsListByRestaurant
                                (restList.get(position).getRestaurantId());

                mIngredientsAdapter.setItems(changedList);
                mIngredientsAdapter.getIngredientPlacement().resetIngredientPlacement();
                mIngredientsAdapter.notifyDataSetChanged();

                mListviewAdapter.resetItem();
                mListviewAdapter.notifyDataSetChanged();



                ingredientDBOtemp.close();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing
            }
        });

        // Show the user how to use the app via an AlertDialog
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setMessage(R.string.order_hints);

        mAlertDialog= builder.create();
        mAlertDialog.setTitle("Order Hints");

        // Attach a listener to the listView. If an item is clicked then the user is prompted
        // to a message asking for a removal of an ingredrient. If the user clicks on yes then
        // the item is removed
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {
                AlertDialog.Builder b= new AlertDialog.Builder(OrderActivity.this);
                b.setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Ingredient ingredient=
                                        (Ingredient) mListviewAdapter.getItem(position);
                                mIngredientsAdapter.remove(ingredient);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setMessage(R.string.remove_item);
                AlertDialog ad= b.create();
                ad.setTitle("Remove?");
                ad.show();
            }
        });

        mPlaceOrderBtn = (Button) findViewById(R.id.button_order);
        mPlaceOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListviewAdapter.getItems().size() > 0){
                    mListviewAdapter.packageIntent(intent);
                    startActivity(intent);
                }else{
                    Toast.makeText(OrderActivity.this, R.string.empty_ing_list,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAlertDialog.show();
        restaurantDBO = new RestaurantDBO(OrderActivity.this);
        ingredientDBO = new IngredientDBO(OrderActivity.this);
    }

    protected void onPause() {
        super.onPause();
        restaurantDBO.close();
        ingredientDBO.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AlertDialog.Builder ab= new AlertDialog.Builder(this);
                ab.setCancelable(false)
                        .setMessage("Do you want to logout?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OrderActivity.this.finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog a= ab.create();
                a.setTitle("Logout?");
                a.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab= new AlertDialog.Builder(this);
        ab.setCancelable(false)
                .setMessage("Do you want to logout?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderActivity.this.finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog a= ab.create();
        a.setTitle("Logout?");
        a.show();
    }

    private String[] getRestNames(ArrayList<Restaurant> list){
        String[] nameList = new String[list.size()];
        for(int i = 0; i < nameList.length; i++){
            nameList[i] = list.get(i).getName();
        }

        return nameList;
    }

}
