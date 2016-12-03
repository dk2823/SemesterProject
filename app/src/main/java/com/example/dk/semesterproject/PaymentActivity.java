package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Eck on 12/3/16.
 */

public class PaymentActivity extends Activity {
    private EditText fullName;
    private EditText cardNumber;
    private EditText securityCode;
    private Button mSubmit;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);

        fullName= (EditText) findViewById(R.id.username_payment);
        cardNumber= (EditText) findViewById(R.id.password_payment);
        securityCode= (EditText) findViewById(R.id.security_payment);
        mSubmit= (Button) findViewById(R.id.submitButton_payment);
        mIntent= getIntent();

        if(!mIntent.hasExtra(OrderActivity.INGREDIENTS)){
            throw new IllegalArgumentException("There's no ingredient");
        }
        final String ingredientIds = mIntent.getExtras().getString(OrderActivity.INGREDIENTS);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fullName.getText().toString().trim().equals("") &&
                        !cardNumber.getText().toString().trim().equals("") &&
                        !securityCode.getText().toString().trim().equals("")) {
                    mIntent.setClass(PaymentActivity.this, OrderSentActivity.class);
                    mIntent.putExtra(OrderActivity.INGREDIENTS, ingredientIds);
                    startActivity(mIntent);
                } else {
                    Toast.makeText(PaymentActivity.this, R.string.entry_missing,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
