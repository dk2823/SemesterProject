package com.example.dk.semesterproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.Ingredient;

/**
 * Created by Eck on 12/2/16.
 */

public class ListviewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Ingredient> ingredientsList;

    public ListviewAdapter(Context context) {
        mContext= context;
        ingredientsList= new ArrayList<Ingredient>();
    }

    public void add(Ingredient ingredient) {
        ingredientsList.add(ingredient);
        notifyDataSetChanged();
    }

    public void packageIntent(Intent intent) {
        intent.putExtra(OrderActivity.INGREDIENTS, getIngredientIds(this.ingredientsList));
    }

    public void remove(Ingredient ingredient) {
        ingredientsList.remove(ingredient);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ingredientsList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView=convertView;
        ViewHolder viewHolder;
        Ingredient ingredient= ingredientsList.get(position);

        if (convertView==null) {
            viewHolder= new ViewHolder();
            newView= LayoutInflater.from(mContext).inflate(R.layout.selected_item, null);
            viewHolder.textView= (TextView) newView.findViewById(R.id.selected_item);
            newView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) newView.getTag();
        }

        viewHolder.textView.setText(ingredient.getName());

        return newView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return ingredientsList.get(position);
    }

    public void resetItem(){
        this.ingredientsList = new ArrayList<Ingredient>();
    }

    private static class ViewHolder {
        private TextView textView;
    }

    private String getIngredientIds(ArrayList<Ingredient> list){
        int length = list.size();
        int lastIndex = length - 1;
        String result = "";

        for(int i = 0; i < length; i++){
            result += list.get(i).getIngredientId();
            if(i != lastIndex){
                result += ",";
            }
        }

        return result;
    }
}
