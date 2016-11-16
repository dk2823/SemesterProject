package com.example.dk.semesterproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity that allows the user to recover his/her password
 */

public class PasswordRecoveryActivity extends Activity {
    private Button mSendPasswordButton;
    private TextView mPasswordRecoveryStatus;
    private EditText mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_recovery);
    }
}
