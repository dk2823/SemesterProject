package com.example.dk.semesterproject;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eck on 11/20/16.
 */

public class IngredientsAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Item> items;
    private RelativeLayout mFrame;

    public static final String BANANA_PEPPERS= "Banana Peppers";
    public static final String BLACK_OLIVES= "Black Olives";
    public static final String CARROT= "carrot";
    public static final String CUCUMBERS= "cucumbers";
    public static final String CROUTONS= "Garlic Croutons";
    public static final String GRAPES= "Red Grapes";
    public static final String ONIONS= "Onions";
    public static final String LETTUCE= "lettuce";
    public static final String CHEESE= "Three Cheese";
    public static final String STRAWBERRY= "Strawberries";
    public static final String TOMATO= "Tomatoes";
    public static final String CHICKEN= "Chicken";
    public static final String TAG= "IngredientsAdapter";

    public IngredientsAdapter(Context context, RelativeLayout frame) {
        mContext= context;
        items= new ArrayList<>();
        init(items);
        mFrame= frame;
    }

    public int getItemResID(int position) {
        return items.get(position).id;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Get the layout for a specific item
        LinearLayout linearLayout= (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.view_pager_item, null);

        // Retrieve the ImageView and the textView and add the layout to the container
        ImageView itemImage= (ImageView) linearLayout.findViewById(R.id.item);
        TextView itemName= (TextView) linearLayout.findViewById(R.id.item_name);
        Item it= items.get(position);

        itemImage.setImageResource(it.id);
        itemName.setText(it.name);

        // Attach a listener to the image view
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientPlacement place= new IngredientPlacement(mContext,mFrame);
                place.addIngredient(TOMATO);
                place.addIngredient(BANANA_PEPPERS);
                place.addIngredient(LETTUCE);
            }
        });

        container.addView(linearLayout);

        return linearLayout;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * Initializes the arraylist of ingredients pictures with their names
     */
    private void init(ArrayList<Item> items) {
        items.add(new Item(R.drawable.banana_peppers, BANANA_PEPPERS));
        items.add(new Item(R.drawable.black_olives, BLACK_OLIVES));
        items.add(new Item(R.drawable.carrot, CARROT));
        items.add(new Item(R.drawable.cucumbers, CUCUMBERS));
        items.add(new Item(R.drawable.garlic_croutons, CROUTONS));
        items.add(new Item(R.drawable.grape, GRAPES));
        items.add(new Item(R.drawable.onions, ONIONS));
        items.add(new Item(R.drawable.romaine_lettuce, LETTUCE));
        items.add(new Item(R.drawable.shredded_cheese, CHEESE));
        items.add(new Item(R.drawable.strawberry, STRAWBERRY));
        items.add(new Item(R.drawable.tomatoes, TOMATO));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    private class Item {
        private int id;
        private String name;

        public Item(int id, String name) {
            this.id= id;
            this.name= name;
        }
    }

}
