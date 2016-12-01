package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OrderConfirmActivity extends Activity {

    private ListView mList;
    private Button mOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_page);

        mOrderBtn = (Button) findViewById(R.id.button_order);
        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent= new Intent(OrderConfirmActivity.this, OrderPickupActivity.class);
                    //intent.putExtra(USERNAME, mUsername.getText().toString().trim());
                    startActivity(intent);

            }
        });

        mList = (ListView) findViewById(R.id.sampleListView);

        String[] testStrArr = new String[5];
        testStrArr[0]= "Hello";
        testStrArr[1] = "test";

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_order_confirm_listview, testStrArr);

        // Second argument will be layout defined in XML file and having TextView for each string in the array.

        // Final argument is an array of strings which will be populated in the text view


        mList.setAdapter(adapter);
    }

}
