package com.example.dk.semesterproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        Log.i(TAG, "I am here");

        mUsername= (TextView) findViewById(R.id.username_order);
        mViewPager= (ViewPager) findViewById(R.id.pager_view);
        mSpinner= (Spinner) findViewById(R.id.restaurant_chooser);
        mFrame= (RelativeLayout) findViewById(R.id.main_layout);

        mIngredientsAdapter= new IngredientsAdapter(this, mFrame);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAlertDialog.show();
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


    /*
     * Private class View that redraws the image selected
     */
    private class ImageSelected extends View {
        private Bitmap mBitmap;
        private float xPos;
        private float yPos;
        private Paint paint;

        public ImageSelected(int res) {
            super(OrderActivity.this);
            Bitmap bitmap= BitmapFactory.decodeResource(OrderActivity.this.getResources(), res);
            mBitmap= Bitmap.createScaledBitmap(bitmap,70,60,false);
            paint= new Paint();
        }

        public void set(float x, float y) {
            xPos= x;
            yPos= y;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            float x= xPos-35;
            float y= yPos-30;
            canvas.drawBitmap(mBitmap, x, y, paint);
        }
    }
}
