package com.example.dk.semesterproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eck on 12/2/16.
 */

public class ListviewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> ingredientsList;

    public ListviewAdapter(Context context) {
        mContext= context;
        ingredientsList= new ArrayList<>();
    }

    public void add(String ingredient) {
        ingredientsList.add(ingredient);
        notifyDataSetChanged();
    }

    public void packageIntent(Intent intent) {
        intent.putExtra(OrderActivity.INGREDIENTS, new ArrayList<String>(ingredientsList));
    }

    public void remove(String ingredient) {
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
        String ingredient= ingredientsList.get(position);

        if (convertView==null) {
            viewHolder= new ViewHolder();
            newView= LayoutInflater.from(mContext).inflate(R.layout.selected_item, null);
            viewHolder.textView= (TextView) newView.findViewById(R.id.selected_item);
            newView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) newView.getTag();
        }

        viewHolder.textView.setText(ingredient);

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

    private static class ViewHolder {
        private TextView textView;
    }
}
