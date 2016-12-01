package com.example.dk.semesterproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
    private static final String TAG= "IngredientPlacement";

    private Map<String, Integer> items;
    private Queue<String> toAdd;
    private Stack<String> plateStack;
    private Context mContext;
    private RelativeLayout mFrame;
    private int mFrameWidth;
    private int mFrameHeight;
    private Bitmap lettuceBitmap;
    private Bitmap tomatoBitmap;
    private Bitmap bananaPepperBitmap;

    /*
     * Initializes toAdd and items. Populate items;
     */
    public IngredientPlacement(Context c, RelativeLayout parent) {
        mContext= c;
        toAdd= new LinkedList<>();
        plateStack= new Stack<>();
        items= new TreeMap<>();
        populate();
        mFrame= parent;

        mFrameWidth= mFrame.getWidth();
        mFrameHeight= mFrame.getHeight();

        // Create a scaled lettuce Bitmap
        Bitmap tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_leaf);
        lettuceBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/2,
                mFrameHeight/2,false);

        // Create a scaled tomato bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_tomato);
        tomatoBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/4,
                mFrameHeight/6,false);

        // Create a scaled banana pepper bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_banana_pepper);
        bananaPepperBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/5,
                mFrameHeight/6,false);
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
                    Lettuce lettuce = new Lettuce(i);
                    mFrame.addView(lettuce);
                }
                break;

            case IngredientsAdapter.TOMATO:
                plateStack.push(IngredientsAdapter.TOMATO);
                for (int i= 0; i<360; i+= 60) {
                    Tomato tomato = new Tomato(i);
                    mFrame.addView(tomato);
                }
                break;

            case IngredientsAdapter.BANANA_PEPPERS:
                plateStack.push(IngredientsAdapter.BANANA_PEPPERS);
                for (int i= 0; i<360; i+= 90) {
                    BananaPepper bp = new BananaPepper(i);
                    mFrame.addView(bp);
                }
                break;
        }
    }

    /*
     * Lettuce class
     */
    private class Lettuce extends ImageView {
        public Lettuce(int rotation) {
            super(mContext);
            setImageBitmap(lettuceBitmap);
            setPivotX(130f);
            setPivotY(300f);
            setRotation(rotation);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/2, mFrameHeight/2);
            params.setMarginStart(mFrameWidth/4);
            setLayoutParams(params);
        }

    }

    /*
     * Tomato class
     */
    private class Tomato extends ImageView {
        public Tomato(int rotation) {
            super(mContext);
            setImageBitmap(tomatoBitmap);
            setPivotX(80f);
            setPivotY(200f);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/4, mFrameHeight/6);
            params.setMargins(mFrameWidth/3, mFrameHeight/6, 0, 0);
            setLayoutParams(params);
            setRotation(rotation);
        }
    }

    /*
     * Banana peppers class
     */

    private class BananaPepper extends ImageView {
        public BananaPepper(int rotation) {
            super(mContext);
            setImageBitmap(bananaPepperBitmap);
            setPivotX(60f);
            setPivotY(260f);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/5, mFrameHeight/6);
            params.setMargins(mFrameWidth/3, mFrameHeight/14, 0, 0);
            setLayoutParams(params);
            setRotation(rotation);
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
