package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import database.UserDBO;
import models.User;

public class MainActivity extends Activity {
    private static final int CREATE_ACCOUNT= 0;
    private static final int RECOVER_PASSWORD= 1;
    public static final String EMAIL="email";
    public static final String USERNAME="username";

    private Button mLogin;
    private Button mRegister;
    private Button mForgotPassword;
    private TextView mStatus;
    private EditText mUsername;
    private EditText mPassword;

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

        // Get the reference to the username and password editext
        mUsername= (EditText) findViewById(R.id.username_main);
        mPassword= (EditText) findViewById(R.id.password_main);

        // Attach listeners to the buttons. When the user clicks on the registration button
        // then the RegisterActivity will start
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authenticate()) {
                    Intent intent= new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra(USERNAME, mUsername.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RegisterActivity
                Intent intent= new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, CREATE_ACCOUNT);
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), PasswordRecoveryActivity.class);
                startActivityForResult(intent, RECOVER_PASSWORD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode==CREATE_ACCOUNT) {
                mStatus.setText(R.string.create_success);
                mUsername.setText(data.getExtras().getString(USERNAME));
            }
            else
                mStatus.setText("Password successfully sent to\n"+
                    data.getExtras().getString(EMAIL));
        }
    }

    /**
     * Authenticates the username and the password. Returns true if the username and the password
     * match a user in the database
     * @return true if the user exists false otherwise
     */
    private boolean authenticate() {
        String username= mUsername.getText().toString().trim();
        String password= mPassword.getText().toString().trim();
        boolean result;

        if (username.equals("") || password.equals("")) {
            mStatus.setText(R.string.entry_missing);
            result= false;
        } else {
            // Authenticate this user by query the database. If the user does not exist
            // set mStatus text to R.string.invalid_user and result should be false.
            // result should be true

            UserDBO userDBO = new UserDBO(MainActivity.this);

            User user = userDBO.getByUsername(username);

            if(user == null){

                mStatus.setText(R.string.invalid_user);
                result = false;
            }else{
                result = user.checkPassword(password) ? true : false;
            }

            userDBO.close();
        }

        return result;
    }

}
