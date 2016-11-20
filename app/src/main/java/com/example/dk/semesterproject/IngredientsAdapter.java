package com.example.dk.semesterproject;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eck on 11/20/16.
 */

public class IngredientsAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Item> items;

    public IngredientsAdapter(Context context) {
        mContext= context;
        items= new ArrayList<>();
        init(items);
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
        items.add(new Item(R.drawable.banana_peppers, "Banana Peppers"));
        items.add(new Item(R.drawable.black_olives, "Black Olives"));
        items.add(new Item(R.drawable.carrot, "Carrots"));
        items.add(new Item(R.drawable.cucumbers, "Cucumbers"));
        items.add(new Item(R.drawable.garlic_croutons, "Garlic Croutons"));
        items.add(new Item(R.drawable.grape, "Red Grapes"));
        items.add(new Item(R.drawable.onions, "Onions"));
        items.add(new Item(R.drawable.romaine_lettuce, "Lettuce"));
        items.add(new Item(R.drawable.shredded_cheese, "Three Cheese"));
        items.add(new Item(R.drawable.strawberry, "Strawberries"));
        items.add(new Item(R.drawable.tomatoes, "Tomatoes"));
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
