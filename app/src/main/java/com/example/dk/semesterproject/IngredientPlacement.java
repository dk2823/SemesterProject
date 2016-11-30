package com.example.dk.semesterproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Created by Eck on 11/29/16.
 */

public class IngredientPlacement {

    private Map<String, Integer> items;
    private Queue<String> toAdd;
    private Stack<String> plateStack;
    private Context mContext;
    private int mXCenter;
    private int mYCenter;
    private int mXStart;
    private int mYStart;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private RelativeLayout mFrame;
    private Bitmap lettuceBitmap;

    /*
     * Initializes toAdd and items. Populate items;
     */
    public IngredientPlacement(Context c, ImageView plate, RelativeLayout parent) {
        mContext= c;
        toAdd= new LinkedList<>();
        plateStack= new Stack<>();
        items= new TreeMap<>();
        populate();
        mFrame= parent;
        mXStart= plate.getLeft();
        mYStart= plate.getTop();
        mWidth= plate.getWidth();
        mHeight= plate.getHeight();

        // Get the center of the plate
        mXCenter= (mXStart+mWidth)/2;
        mYCenter= (mYStart+mHeight)/2;

        // Get the radius of the plate
        mRadius= mYCenter-mYStart;

        // Create a scaled lettuce Bitmap
        Bitmap tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_leaf);
        lettuceBitmap= Bitmap.createScaledBitmap(tempBitmap,mWidth/2,mHeight/2,false);

    }

    /*
     * Adds an ingredient to the plate
     */
    public boolean addIngredient(String ingredient) {
        if (plateStack.contains(ingredient))
            return false;

        // Add the item temporarily to the queue of the items to add
        toAdd.add(ingredient);

        // Retrieve the priority of the item to add
        int priority_toAdd= items.get(ingredient);

        while (!plateStack.empty()) {
            int priority= items.get(plateStack.peek());
            if (priority < priority_toAdd)
                toAdd.add(plateStack.pop());
            else
                break;
        }

        // Add all the items now
        while (!toAdd.isEmpty())
            addIngredientNow(toAdd.remove());

        return true;
    }

    private void addIngredientNow(String ingredient) {
        switch (ingredient) {
            case IngredientsAdapter.LETTUCE:
                plateStack.push(IngredientsAdapter.LETTUCE);
                for (int i= 0; i<360; i+= 45) {
                    // Convert to radians and add pi/2
                    double angle= Math.PI*i/180+Math.PI/2;

                    // Calculate the coordinates of the lettuce
                    int x= (int) (mRadius * Math.cos(angle));
                    int y= (int) (mRadius * Math.sin(angle));
                    x= mXCenter-x;
                    y= mYCenter-y;

                    Lettuce lettuce= new Lettuce(i,x,y);
                    mFrame.addView(lettuce);
                    lettuce.invalidate();
                }
                break;
        }
    }

    /*
     * Lettuce class
     */
    private class Lettuce extends View {
        private float rotation;
        private int xPos;
        private int yPos;
        private Paint paint;

        public Lettuce(float r, int x, int y) {
            super(mContext);
            rotation= r;
            xPos= x;
            yPos= y;
            paint= new Paint();
            paint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.rotate(rotation,(float)xPos, (float)yPos);
            canvas.drawBitmap(lettuceBitmap, xPos,yPos, paint);
            canvas.restore();
        }
    }


    /*
     * Populates the list of items
     */
    private void populate() {
        items.put(IngredientsAdapter.BANANA_PEPPERS, 1);
        items.put(IngredientsAdapter.BLACK_OLIVES, 1);
        items.put(IngredientsAdapter.CARROT, 1);
        items.put(IngredientsAdapter.CUCUMBERS, 1);
        items.put(IngredientsAdapter.CROUTONS, 1);
        items.put(IngredientsAdapter.GRAPES, 1);
        items.put(IngredientsAdapter.LETTUCE, 3);
        items.put(IngredientsAdapter.CHICKEN, 2);
        items.put(IngredientsAdapter.CHEESE, 1);
        items.put(IngredientsAdapter.STRAWBERRY, 1);
        items.put(IngredientsAdapter.TOMATO, 2);
    }

}
