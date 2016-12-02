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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Eck on 11/20/16.
 */

public class OrderActivity extends Activity {
    private static final String TAG= "OrderActivity";

    private TextView mUsername;
    private ViewPager mViewPager;
    private IngredientsAdapter mIngredientsAdapter;
    private ListviewAdapter mListviewAdapter;
    private ListView mListView;
    private Spinner mSpinner;
    private RelativeLayout mFrame;
    private ArrayAdapter<CharSequence> mSpinnerAdapter;
    private AlertDialog mAlertDialog;
    private Button mPlaceOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        mPlaceOrderBtn = (Button) findViewById(R.id.button_order);
        mPlaceOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(OrderActivity.this, OrderConfirmActivity.class);
                //intent.putExtra(USERNAME, mUsername.getText().toString().trim());
                startActivity(intent);

            }
        });

        mListView= (ListView) findViewById(R.id.ingredients_list);
        mUsername= (TextView) findViewById(R.id.username_order);
        mViewPager= (ViewPager) findViewById(R.id.pager_view);
        mSpinner= (Spinner) findViewById(R.id.restaurant_chooser);
        mFrame= (RelativeLayout) findViewById(R.id.salad_order);

        mListviewAdapter= new ListviewAdapter(getApplicationContext());
        mSpinnerAdapter= ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.restaurants, R.layout.spinner_item);
        mSpinnerAdapter.setDropDownViewResource(R.layout.spinner_item);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO
            }
        });

        mSpinner.setAdapter(mSpinnerAdapter);
        mListView.setAdapter(mListviewAdapter);
        mIngredientsAdapter= new IngredientsAdapter(this, mListviewAdapter, mFrame);
        mViewPager.setAdapter(mIngredientsAdapter);

        // Retrieve the username from the intent and set the username
        String username= getIntent().getExtras().getString(MainActivity.USERNAME);
        mUsername.setText(username);

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
                                String ingredient= (String) mListviewAdapter.getItem(position);
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAlertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
