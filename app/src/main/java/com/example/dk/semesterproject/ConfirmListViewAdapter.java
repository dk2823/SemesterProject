package com.example.dk.semesterproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import models.Ingredient;

/**
 * Created by DK on 12/3/16.
 */

public class ConfirmListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Ingredient> items;


    public ConfirmListViewAdapter(Context c, ArrayList<Ingredient> list){
        this.mContext = c;
        this.mInflater = LayoutInflater.from(c);
        this.items = list;
    }
    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ?
                getItems().get(position).getIngredientId() : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder holder;

        if(v == null){
            v = mInflater.inflate(R.layout.confirm_list_eachline, viewGroup, false);
            holder = new ViewHolder();
            holder.ingredientImg = (ImageView) v.findViewById(R.id.ivConfirmEach);
            holder.txName = (TextView) v.findViewById(R.id.tvConfirmEachName);
            holder.txInfo = (TextView) v.findViewById(R.id.tvConfirmEachInfo);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }

        Ingredient currItem = (Ingredient) getItem(position);

        if(currItem != null){

            holder.ingredientImg.setImageResource(currItem.getImageId());
            holder.txName.setText(currItem.getName());
            holder.txInfo.setText("$" + new DecimalFormat("#.00").format(currItem.getPrice()));
        }
        return v;
    }

    public ArrayList<Ingredient> getItems(){
        return this.items;
    }

    public void setItems(ArrayList<Ingredient> list){
        this.items = list;
    }

    class ViewHolder{
        ImageView ingredientImg;
        TextView txName;
        TextView txInfo;

    }

}
