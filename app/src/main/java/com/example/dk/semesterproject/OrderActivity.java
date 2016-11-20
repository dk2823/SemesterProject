package com.example.dk.semesterproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Eck on 11/20/16.
 */

public class OrderActivity extends Activity {
    private static final int SMALL= 10;
    private static final int MEDIUM= 15;
    private static final int LARGE= 20;

    private TextView mUsername;
    private ViewPager mViewPager;
    private IngredientsAdapter mIngredientsAdapter;
    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> mSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        mUsername= (TextView) findViewById(R.id.username_order);
        mViewPager= (ViewPager) findViewById(R.id.pager_view);
        mSpinner= (Spinner) findViewById(R.id.restaurant_chooser);

        mIngredientsAdapter= new IngredientsAdapter(this);
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
        mViewPager.setAdapter(mIngredientsAdapter);
        mSpinner.setAdapter(mSpinnerAdapter);

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
