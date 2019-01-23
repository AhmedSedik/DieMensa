package com.example.ahmed.diemensa;

import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class DishAdapterTest extends ArrayAdapter<Dish> {


    public DishAdapterTest(@NonNull Context context, List<Dish> dishes) {
        super(context,0,dishes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        ViewHolder holder = new ViewHolder();


        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_dish,
                    parent,false);


            holder.daytimeTextView = convertView.findViewById(R.id.daytime_text_view);
            holder.dishTextView = convertView.findViewById(R.id.dish_text_view);
            holder.componentTextView = convertView.findViewById(R.id.component_text_view);
            holder.priceTextView = convertView.findViewById(R.id.price_text_view);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Dish dish = getItem(position);

            holder.daytimeTextView.setText(dish.getmDayTime());

            holder.dishTextView.setText(dish.getmDish());
            holder.componentTextView.setText(dish.getmComponent());
            String format2 = new DecimalFormat("#,###.00").format(dish.getmPrice());
            holder.priceTextView.setText(format2);



        return convertView;
    }
    static class ViewHolder {
        // The ViewHolder design pattern
        TextView daytimeTextView;
        TextView dishTextView;
        TextView componentTextView;
        TextView priceTextView;
    }

}
