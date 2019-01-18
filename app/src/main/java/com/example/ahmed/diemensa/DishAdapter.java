package com.example.ahmed.diemensa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {



    public DishAdapter(@NonNull Context context, List<Dish> dishes) {
        super(context,0,dishes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listDishesView = convertView;

        if (listDishesView==null){
            listDishesView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_dish,
                    parent,false);

        }

        Dish dish = getItem(position);

        TextView daytimeTextView = listDishesView.findViewById(R.id.daytime_text_view);
        TextView dishTextView = listDishesView.findViewById(R.id.dish_text_view);
        TextView componentTextView = listDishesView.findViewById(R.id.component_text_view);
        if(dish!=null){
            daytimeTextView.setText(dish.getmDayTime());
        }
        dishTextView.setText(dish.getmDish());
        componentTextView.setText(dish.getmComponent());


        return listDishesView;
    }
}
