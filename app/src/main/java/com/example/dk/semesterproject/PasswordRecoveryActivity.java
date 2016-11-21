package com.example.dk.semesterproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Activity that allows the user to recover his/her password
 */

public class PasswordRecoveryActivity extends Activity {
    private Context mContext;
    private Button mSendPasswordButton;
    private TextView mPasswordRecoveryStatus;
    private EditText mUsername;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_recovery);

        mContext= this;

        mSendPasswordButton= (Button) findViewById(R.id.emailButton);
        mPasswordRecoveryStatus= (TextView) findViewById(R.id.passwordRecoveryStatus);
        mUsername= (EditText) findViewById(R.id.username_recovery);
        username= mUsername.getText().toString().trim();

        mSendPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.equals(""))
                    mPasswordRecoveryStatus.setText(R.string.entry_missing);
                else {
                    // Query the database to retrieve the user
                    User user= getUsernameAndPassword();
                    if (user != null)
                        new SendEmail().execute(user);
                    else
                        mPasswordRecoveryStatus.setText(R.string.invalid_username);
                }

            }
        });
    }


    private class SendEmail extends AsyncTask<User, String, String> {
        private static final String TAG= "SendEmail";
        private static final String FROM_EMAIL= "saladBarApp@gmail.com";
        private static final String PASSWORD= "EckDKAlexAndrew";
        private static final String EMAIL_HOST= "smtp.gmail.com";
        private static final String SMTP_AUTH= "true";
        private static final String START_TLS= SMTP_AUTH;
        private static final String PORT= "587";
        private static final String PLAIN= "text/plain";

        private ProgressDialog progressDialog;
        private Session session;
        private Properties properties;
        private MimeMessage message;
        Transport transport;

        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(mContext);
            progressDialog.setMessage("Setting up...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            progressDialog.setMessage(values[0]);
        }

        @Override
        protected String doInBackground(User... params) {
            // Set up the properties
            publishProgress("Processing input...");
            properties= System.getProperties();
            properties.put("mail.smtp.port", PORT);
            properties.put("mail.smtp.auth", SMTP_AUTH);
            properties.put("mail.smtp.starttls.enable", START_TLS);

            // Retrieve the user
            User user= params[0];

            try {
                // Prepare the message
                Thread.sleep(2000);
                publishProgress("Preparing the message...");
                session= Session.getDefaultInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                    }
                });
                message= new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.email));
                message.setSubject(mContext.getString(R.string.email_subject));
                message.setContent(mContext.getString(R.string.email_body) + user +
                        mContext.getString(R.string.email_footer), PLAIN);

                // Send the email
                Thread.sleep(2000);
                publishProgress("Sending email to " + user.email + "...");
                transport = session.getTransport("smtp");
                transport.connect(EMAIL_HOST, FROM_EMAIL, PASSWORD);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();

                // Message sent
                Thread.sleep(2000);
                publishProgress("Email sent");

                Thread.sleep(2000);

            } catch (MessagingException e) {
                Log.e(TAG, "Messaging exception");
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread Interrupted");
            }

            return user.email;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Intent intent= new Intent();
            intent.putExtra(MainActivity.EMAIL, result);
            PasswordRecoveryActivity.this.setResult(RESULT_OK, intent);
            PasswordRecoveryActivity.this.finish();
        }
    }

    /**
     * Retrieves the username and the password based on the email provided
     * @return the User object containing the username and the password of the user
     */
    private User getUsernameAndPassword() {

        // TODO Query the database based on the username. Create the user object and return it


        return null;
    }

    /**
     * User class
     */
    private class User {
        private String username;
        private String password;
        private String email;

        public User(String username, String password, String email) {
            this.username= username;
            this.password= password;
            this.email= email;
        }

        @Override
        public String toString() {
            return "Username: " + username + "\nPassword: " + password;
        }
    }

}
