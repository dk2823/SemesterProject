package com.example.dk.semesterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Register Page. Provides means to the user to register
 */

public class RegisterActivity extends Activity {
    private static final int SUCCESS= 0;
    private static final int USERNAME_IN_USE= 1;
    private static final int PASSWORDS_DO_NOT_MATCH= 2;
    private static final int ENTRY_MISSING= 3;

    private Button mRegister;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirm;
    private TextView mRegistrationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        // Get the textViews and the button reference
        mRegister= (Button) findViewById(R.id.registerButton);
        mUsername= (EditText) findViewById(R.id.username);
        mPassword= (EditText) findViewById(R.id.password);
        mConfirm= (EditText) findViewById(R.id.confirm_password);
        mRegistrationStatus= (TextView) findViewById(R.id.registrationStatus);

        // When the Register button is pressed, attempt to register this user.
        // If it is successful send the user back to the MainActivity
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (check()) {
                    case SUCCESS:
                        Intent intent= new Intent();
                        intent.putExtra(MainActivity.USERNAME,
                                mUsername.getText().toString().trim());
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case USERNAME_IN_USE:
                        mRegistrationStatus.setText(R.string.username_in_use);
                        break;
                    case PASSWORDS_DO_NOT_MATCH:
                        mRegistrationStatus.setText(R.string.password_not_matching_confirm);
                        break;
                    default:
                        mRegistrationStatus.setText(R.string.entry_missing);
                }
            }
        });
    }

    /**
     * Returns SUCCESS if username is created otherwise returns the appropriate code
     */
    private int check() {
        String username= mUsername.getText().toString().trim();
        String password= mPassword.getText().toString();
        String confirmPassword= mConfirm.getText().toString();

        if (!username.equals("") && !password.equals("") && !confirmPassword.equals("")) {
            if (true)// TODO Check whether the username is in use
            {
                // Check whether the password matches the confirmation password
                if (password.equals(confirmPassword)) {
                    // TODO Store the user credentials into the database then return SUCCESS

                    return SUCCESS;
                }
                else
                    return PASSWORDS_DO_NOT_MATCH;
            }
        }
        return ENTRY_MISSING;
    }
}