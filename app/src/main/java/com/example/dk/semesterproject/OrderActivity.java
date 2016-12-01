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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Eck on 11/20/16.
 */

public class OrderActivity extends Activity {
    private static final int SMALL= 10;
    private static final int MEDIUM= 15;
    private static final int LARGE= 20;
    private static final String TAG= "OrderActivity";

    private TextView mUsername;
    private ViewPager mViewPager;
    private IngredientsAdapter mIngredientsAdapter;
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



        Log.i(TAG, "I am here now");

        mUsername= (TextView) findViewById(R.id.username_order);
        mViewPager= (ViewPager) findViewById(R.id.pager_view);
        mSpinner= (Spinner) findViewById(R.id.restaurant_chooser);
        mFrame= (RelativeLayout) findViewById(R.id.salad_order);

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
        // Retrieve the username from the intent and set the username
        String username= getIntent().getExtras().getString(MainActivity.USERNAME);
        mUsername.setText(username);

        // Set the adapters for the view pager and the spinner
        mSpinner.setAdapter(mSpinnerAdapter);

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
        mAlertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mIngredientsAdapter= new IngredientsAdapter(this, mFrame);
            mViewPager.setAdapter(mIngredientsAdapter);
        }
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
