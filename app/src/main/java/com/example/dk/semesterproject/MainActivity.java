package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button mLogin;
    private Button mRegister;
    private Button mForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the buttons references
        mLogin= (Button) findViewById(R.id.login);
        mRegister= (Button) findViewById(R.id.registerHelp);
        mForgotPassword= (Button) findViewById(R.id.forgotPasswordHelp);

        // Attach listeners to the buttons
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RegisterActivity
                Intent intent= new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
