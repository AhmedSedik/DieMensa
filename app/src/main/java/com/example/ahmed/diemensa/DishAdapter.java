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
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class DishAdapter extends ArrayAdapter<Dish> {


    Intent intent;
    public DishAdapter(@NonNull Context context, List<Dish> dishes) {
        super(context,0,dishes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        TextView priceTextView = listDishesView.findViewById(R.id.price_text_view);

        if(dish!=null){
            daytimeTextView.setText(dish.getmDayTime());
        }
        dishTextView.setText(dish.getmDish());
        componentTextView.setText(dish.getmComponent());
        String price = Double.toString(dish.getmPrice());
        String pr = String.valueOf(dish.getmPrice());

        String format2 = new DecimalFormat("#,###.00").format(dish.getmPrice());
        String formattedPrice = formatPrice(dish.getmPrice());

        //not preferable method
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        String currency = format.format(dish.getmPrice());
        priceTextView.setText(format2 + " €");

        return listDishesView;
    }

    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("0.0");

        return formatter.format(price);
    }

}
