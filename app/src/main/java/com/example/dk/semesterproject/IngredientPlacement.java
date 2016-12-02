package com.example.dk.semesterproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
    private Bitmap oliveBitmap;
    private Bitmap carrotBitmap;
    private Bitmap cucumberBitmap;
    private Bitmap croutonBitmap;
    private Bitmap grapeBitmap;
    private Bitmap onionBitmap;
    private Bitmap strawberryBitmap;
    private Bitmap chickenBitmap;
    private Bitmap cheeseBitmap;
    private float pivotX;
    private float pivotY;

    /*
     * Initializes toAdd and items. Populate items;
     */
    public IngredientPlacement(Context c) {
        mContext= c;
        toAdd= new LinkedList<>();
        plateStack= new Stack<>();
        items= new TreeMap<>();
        populate();

    }

    /*
     * Sets all the bitmap. Needs to be called before addIngredient is called
     */
    public void setUp(RelativeLayout frame) {
        mFrame= frame;

        mFrameWidth= mFrame.getWidth();
        mFrameHeight= mFrame.getHeight();

        // pivot will be the center of the plate
        // First we need to convert the padding dp into pixels
        pivotX= mFrameWidth/2.0f;
        pivotY= mFrameHeight/2.0f;

        // Create a scaled lettuce Bitmap
        Bitmap tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_leaf);
        lettuceBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/2,
                mFrameHeight/2,false);

        // Create a scaled tomato bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_tomato);
        tomatoBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/8,
                mFrameHeight/6,false);

        // Create a scaled banana pepper bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_banana_pepper);
        bananaPepperBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/5,
                mFrameHeight/6,false);

        // Create a scaled black olive bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_olive);
        oliveBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/6,
                mFrameHeight/6,false);

        // Create a scaled carrot bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_carrot);
        carrotBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/8,
                mFrameHeight/8,false);

        // Create a scaled cucumber bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_cucumber);
        cucumberBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/8,
                mFrameHeight/8,false);

        // Create a scaled crouton bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_crouton);
        croutonBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/8,
                mFrameHeight/8,false);

        // Create a scaled grape bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_grape);
        grapeBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/8,
                mFrameHeight/8,false);

        // Create a scaled onion bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_onion);
        onionBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/5,
                mFrameHeight/5,false);

        // Create a scaled strawberry bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_strawberry);
        strawberryBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/6,
                mFrameHeight/6,false);

        // Create a scaled three cheese bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.single_three_cheese);
        cheeseBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/4,
                mFrameHeight/4,false);

        // Create a scaled chicken bitmap
        tempBitmap= BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.chicken);
        chickenBitmap= Bitmap.createScaledBitmap(tempBitmap,mFrameWidth/2,
                mFrameHeight/2,false);

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
                for (int i=0; i<360; i+= 45) {
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

            case IngredientsAdapter.BLACK_OLIVES:
                plateStack.push(IngredientsAdapter.BLACK_OLIVES);
                Olive[] olives= new Olive[4];
                olives[0]= new Olive(mFrameWidth/4, mFrameHeight/8);
                olives[1]= new Olive(mFrameWidth/5*3, mFrameHeight/8);
                olives[2]= new Olive(mFrameWidth/4, mFrameHeight/5*3);
                olives[3]= new Olive(mFrameWidth/5*3, mFrameHeight/5*3);
                for (int i= 0; i<olives.length; i++) {;
                    mFrame.addView(olives[i]);
                }
                break;

            case IngredientsAdapter.CARROT:
                plateStack.push(IngredientsAdapter.CARROT);
                for (int i= 1; i<8; i+= 4) {
                    for (int j= 1; j<3; j++) {
                        Carrot carrot = new Carrot(mFrameWidth/4*j, mFrameHeight/8*i);
                        mFrame.addView(carrot);
                    }
                }
                break;

            case IngredientsAdapter.CUCUMBERS:
                plateStack.push(IngredientsAdapter.CUCUMBERS);
                for (int i= 0; i<2; i++) {
                    Cucumber c = new Cucumber(i);
                    mFrame.addView(c);
                }
                break;

            case IngredientsAdapter.CROUTONS:
                plateStack.push(IngredientsAdapter.CROUTONS);
                Crouton[] croutons= new Crouton[5];
                croutons[0]= new Crouton(mFrameWidth/4, mFrameHeight/4);
                croutons[1]= new Crouton(mFrameWidth/24*11, mFrameHeight/3);
                croutons[2]= new Crouton(mFrameWidth/3*2, mFrameHeight/4);
                croutons[3]= new Crouton(mFrameWidth/4, mFrameHeight/2);
                croutons[4]= new Crouton(mFrameWidth/24*11, mFrameHeight/3*2);
                for (int i= 0; i<croutons.length; i++) {
                    mFrame.addView(croutons[i]);
                }
                break;

            case IngredientsAdapter.GRAPES:
                plateStack.push(IngredientsAdapter.GRAPES);
                Grape[] grapes= new Grape[3];
                grapes[0]= new Grape(mFrameWidth/6, mFrameHeight/9*4);
                grapes[1]= new Grape(mFrameWidth/24*11, mFrameHeight/7*4);
                grapes[2]= new Grape(mFrameWidth/10*7, mFrameHeight/9*4);
                for (int i=0; i<grapes.length; i++) {
                    mFrame.addView(grapes[i]);
                }
                break;

            case IngredientsAdapter.ONIONS:
                plateStack.push(IngredientsAdapter.ONIONS);
                for (int i= 0; i<360; i+= 60) {
                    Onion onion = new Onion(i);
                    mFrame.addView(onion);
                }
                break;

            case IngredientsAdapter.STRAWBERRY:
                plateStack.push(IngredientsAdapter.STRAWBERRY);
                Strawberry[] strawberries= new Strawberry[3];
                strawberries[0]= new Strawberry(mFrameWidth/2, mFrameHeight/4);
                strawberries[1]= new Strawberry(mFrameWidth/10*3, mFrameHeight/4);
                strawberries[2]= new Strawberry(mFrameWidth/5*2, mFrameHeight/5*2);
                for (int i=0; i<strawberries.length; i++) {
                    mFrame.addView(strawberries[i]);
                }
                break;

            case IngredientsAdapter.CHEESE:
                plateStack.push(IngredientsAdapter.CHEESE);
                Cheese[] cheese= new Cheese[9];
                cheese[0]= new Cheese(mFrameWidth/6, mFrameHeight/4);
                cheese[1]= new Cheese(mFrameWidth/3, mFrameHeight/4);
                cheese[2]= new Cheese(mFrameWidth/6, mFrameHeight/3);
                cheese[3]= new Cheese(mFrameWidth/3, mFrameHeight/3);
                cheese[4]= new Cheese(mFrameWidth/2, mFrameHeight/4);
                cheese[5]= new Cheese(mFrameWidth/2, mFrameHeight/3);
                cheese[6]= new Cheese(mFrameWidth/6, mFrameHeight/2);
                cheese[7]= new Cheese(mFrameWidth/3, mFrameHeight/2);
                cheese[8]= new Cheese(mFrameWidth/2, mFrameHeight/2);
                for (int i=0; i<cheese.length; i++) {
                    mFrame.addView(cheese[i]);
                }
                break;

            default:
                plateStack.push(IngredientsAdapter.CHICKEN);
                Chicken chicken= new Chicken();
                mFrame.addView(chicken);
        }
    }

    /*
     * Removes an ingredient from the list
     */
    public void remove(String ingredient) {
        switch (ingredient) {
            case IngredientsAdapter.BANANA_PEPPERS:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof BananaPepper)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.BANANA_PEPPERS);
                break;

            case IngredientsAdapter.BLACK_OLIVES:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Olive)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.BLACK_OLIVES);
                break;

            case IngredientsAdapter.CARROT:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Carrot)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.CARROT);
                break;

            case IngredientsAdapter.CUCUMBERS:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Cucumber) {
                        mFrame.removeView(v);
                    }
                }
                plateStack.remove(IngredientsAdapter.CUCUMBERS);
                break;

            case IngredientsAdapter.CROUTONS:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Crouton)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.CROUTONS);
                break;

            case IngredientsAdapter.GRAPES:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Grape)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.GRAPES);
                break;

            case IngredientsAdapter.ONIONS:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Onion)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.ONIONS);
                break;

            case IngredientsAdapter.LETTUCE:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Lettuce)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.LETTUCE);
                break;

            case IngredientsAdapter.CHEESE:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Cheese)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.CHEESE);
                break;

            case IngredientsAdapter.STRAWBERRY:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Strawberry)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.STRAWBERRY);
                break;

            case IngredientsAdapter.TOMATO:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Tomato)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.TOMATO);
                break;

            default:
                for (int i=0; i<mFrame.getChildCount(); i++) {
                    View v= mFrame.getChildAt(i);
                    if (v instanceof Chicken)
                        mFrame.removeView(v);
                }
                plateStack.remove(IngredientsAdapter.CHICKEN);
        }
    }


    /*
     * Lettuce class
     */
    private class Lettuce extends ImageView {
        public Lettuce(int rotation) {
            super(mContext);
            setImageBitmap(lettuceBitmap);
            setPivotX(pivotX);
            setPivotY(pivotY);
            setRotation(rotation);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth, mFrameHeight/2);
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
            setPivotX(pivotX);
            setPivotY(pivotY);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth, mFrameHeight/3);
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
            setPivotX(pivotX);
            setPivotY(pivotY);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth, mFrameHeight/4);
            setLayoutParams(params);
            setRotation(rotation);
        }
    }

    /*
     * Banana peppers class
     */

    private class Olive extends ImageView {
        public Olive(int leftMargin, int topMargin) {
            super(mContext);
            setImageBitmap(oliveBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/6, mFrameHeight/6);
            params.setMargins(leftMargin,topMargin,0,0);
            setLayoutParams(params);
        }
    }

    /*
     * Carrot class
     */

    private class Carrot extends ImageView {
        public Carrot(int leftMargin, int topMargin) {
            super(mContext);
            setImageBitmap(carrotBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/5, mFrameHeight/5);
            params.setMargins(leftMargin,topMargin,0,0);
            setLayoutParams(params);
        }
    }

    /*
     * Cucumber class
     */
    private class Cucumber extends ImageView {
        public Cucumber(int num) {
            super(mContext);
            setImageBitmap(cucumberBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/5, mFrameHeight/5);
            switch (num) {
                case 1: params.setMargins(mFrameWidth/4,mFrameHeight/3,0,0); break;
                default: params.setMargins(mFrameWidth/2,mFrameHeight/3,0,0);
            }
            setLayoutParams(params);
        }
    }

    /*
     * Crouton class
     */
    private class Crouton extends ImageView {
        public Crouton(int leftMargin, int topMargin) {
            super(mContext);
            setImageBitmap(croutonBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/8, mFrameHeight/8);

            params.setMargins(leftMargin,topMargin,0,0);
            setLayoutParams(params);
        }
    }

    /*
     * Grape class
     */
    private class Grape extends ImageView {
        public Grape(int leftMargin, int topMargin) {
            super(mContext);
            setImageBitmap(grapeBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/8, mFrameHeight/8);

            params.setMargins(leftMargin,topMargin,0,0);
            setLayoutParams(params);
        }
    }

    /*
     * Onion class
     */
    private class Onion extends ImageView {
        public Onion(int rotation) {
            super(mContext);
            setImageBitmap(onionBitmap);
            setPivotX(pivotX);
            setPivotY(pivotY);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth, mFrameHeight/3);
            setLayoutParams(params);
            setRotation(rotation);
        }
    }

    /*
     * Strawberry class
     */
    private class Strawberry extends ImageView {
        public Strawberry(int leftMargin, int topMargin) {
            super(mContext);
            setImageBitmap(strawberryBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/6, mFrameHeight/6);

            params.setMargins(leftMargin,topMargin,0,0);
            setLayoutParams(params);
        }
    }

    /*
     * Cheese class
     */
    private class Cheese extends ImageView {
        public Cheese(int leftMargin, int topMargin) {
            super(mContext);
            setImageBitmap(cheeseBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/4, mFrameHeight/4);

            params.setMargins(leftMargin,topMargin,0,0);
            setLayoutParams(params);
        }
    }

    /*
     * Chicken class
     */
    private class Chicken extends ImageView {
        public Chicken() {
            super(mContext);
            setImageBitmap(chickenBitmap);
            RelativeLayout.LayoutParams params=
                    new RelativeLayout.LayoutParams(mFrameWidth/10*7, mFrameHeight/10*7);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            setLayoutParams(params);
        }
    }
    /*
     * Populates the list of items
     */
    private void populate() {
        items.put(IngredientsAdapter.BANANA_PEPPERS, 1);
        items.put(IngredientsAdapter.BLACK_OLIVES, 1);
        items.put(IngredientsAdapter.CARROT, 2);
        items.put(IngredientsAdapter.CUCUMBERS, 1);
        items.put(IngredientsAdapter.CROUTONS, 1);
        items.put(IngredientsAdapter.GRAPES, 1);
        items.put(IngredientsAdapter.LETTUCE, 4);
        items.put(IngredientsAdapter.CHICKEN, 3);
        items.put(IngredientsAdapter.CHEESE, 0);
        items.put(IngredientsAdapter.STRAWBERRY, 1);
        items.put(IngredientsAdapter.TOMATO, 2);
        items.put(IngredientsAdapter.ONIONS, 1);
    }

}
