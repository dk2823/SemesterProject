package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int CREATE_ACCOUNT= 0;

    private Button mLogin;
    private Button mRegister;
    private Button mForgotPassword;
    private TextView mStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the buttons references
        mLogin= (Button) findViewById(R.id.login);
        mRegister= (Button) findViewById(R.id.registerHelp);
        mForgotPassword= (Button) findViewById(R.id.forgotPasswordHelp);

        // Get the status textView
        mStatus= (TextView) findViewById(R.id.registrationOrpasswordRecoveryResult);

        // Get the reference to the textview to display whether the account creation was a success
        // or not, or whether the password recovery email has been sent

        // Attach listeners to the buttons. When the user clicks on the registration button
        // then the RegisterActivity will start
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RegisterActivity
                Intent intent= new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, CREATE_ACCOUNT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CREATE_ACCOUNT && resultCode==RESULT_OK) {
            mStatus.setText(R.string.create_success);
        }
    }
}
