package com.example.dk.semesterproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import models.Ingredient;

/**
 * Created by Eck on 11/20/16.
 */

public class IngredientsAdapter extends PagerAdapter {
    private Context mContext;
//    private ArrayList<Item> items;
    private ArrayList<Ingredient> items;
    private RelativeLayout mFrame;
    private IngredientPlacement ingredientPlacement;
    private ListviewAdapter mListviewAdapter;

    public static final String BANANA_PEPPERS= "Banana Peppers";
    public static final String BLACK_OLIVES= "Black Olives";
    public static final String CARROT= "Carrot";
    public static final String CUCUMBERS= "Cucumbers";
    public static final String CROUTONS= "Garlic Croutons";
    public static final String GRAPES= "Red Grapes";
    public static final String ONIONS= "Onions";
    public static final String LETTUCE= "Lettuce";
    public static final String CHEESE= "Three Cheese";
    public static final String STRAWBERRY= "Strawberries";
    public static final String TOMATO= "Tomatoes";
    public static final String CHICKEN= "Chicken";
    public static final String TAG= "IngredientsAdapter";

    public IngredientsAdapter(Context context, ListviewAdapter adapter,
                              RelativeLayout frame, ArrayList<Ingredient> list) {
        mContext= context;
        items = list;
//        items= new ArrayList<>();
//        init(items);
        ingredientPlacement= new IngredientPlacement(context);
        mListviewAdapter= adapter;
        mFrame= frame;
    }

    public void remove(Ingredient ingredient) {
        ingredientPlacement.remove(ingredient.getName());
        mListviewAdapter.remove(ingredient);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Get the layout for a specific item
        LinearLayout linearLayout= (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.view_pager_item, null);


        // Retrieve the ImageView and the textView and add the layout to the container
        ImageView itemImage= (ImageView) linearLayout.findViewById(R.id.item);
        TextView itemName= (TextView) linearLayout.findViewById(R.id.item_name);
        final Ingredient it= items.get(position);

        /*
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap img = BitmapFactory.decodeResource(mContext.getResources(), it.getImageId(), options);
        itemImage.setImageBitmap(img);
        */
        itemImage.setImageResource(it.getImageId());
        itemName.setText(it.getName());

        // Attach a listener to the image view
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientPlacement.setUp(mFrame);
                switch (it.getName()) {
                    case BANANA_PEPPERS:
                        if (ingredientPlacement.addIngredient(BANANA_PEPPERS))
                            mListviewAdapter.add(it);
                        break;
                    case TOMATO:
                        if (ingredientPlacement.addIngredient(TOMATO))
                            mListviewAdapter.add(it);
                        break;
                    case LETTUCE:
                        if (ingredientPlacement.addIngredient(LETTUCE))
                            mListviewAdapter.add(it);
                        break;
                    case BLACK_OLIVES:
                        if (ingredientPlacement.addIngredient(BLACK_OLIVES))
                            mListviewAdapter.add(it);
                        break;
                    case CARROT:
                        if (ingredientPlacement.addIngredient(CARROT))
                            mListviewAdapter.add(it);
                        break;
                    case CUCUMBERS:
                        if (ingredientPlacement.addIngredient(CUCUMBERS))
                            mListviewAdapter.add(it);
                        break;
                    case CROUTONS:
                        if (ingredientPlacement.addIngredient(CROUTONS))
                            mListviewAdapter.add(it);
                        break;
                    case GRAPES:
                        if (ingredientPlacement.addIngredient(GRAPES))
                            mListviewAdapter.add(it);
                        break;
                    case ONIONS:
                        if (ingredientPlacement.addIngredient(ONIONS))
                            mListviewAdapter.add(it);
                        break;
                    case STRAWBERRY:
                        if (ingredientPlacement.addIngredient(STRAWBERRY))
                            mListviewAdapter.add(it);
                        break;
                    case CHEESE:
                        if (ingredientPlacement.addIngredient(CHEESE))
                            mListviewAdapter.add(it);
                        break;
                    default:
                        if (ingredientPlacement.addIngredient(CHICKEN))
                            mListviewAdapter.add(it);
                }
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

    public void setItems(ArrayList<Ingredient> list){
        this.items = list;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public IngredientPlacement getIngredientPlacement(){
        return this.ingredientPlacement;
    }


    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * Initializes the arraylist of ingredients pictures with their names
     */
    /*
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
        items.add(new Item(R.drawable.chicken, CHICKEN));

        test(R.drawable.chicken);
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

    */
}
