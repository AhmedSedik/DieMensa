package com.example.ahmed.diemensa;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
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


                if(position %2 == 1){
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.font_green));
                    notifyDataSetChanged();
                } else {
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.background));
                    notifyDataSetChanged();
                }




          /*  int i = 0;
            while(i<=position){
                if (i==position) {
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.font_green));
                } else if (i==position+1) {
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.font_white));
                }
                    i++;
            }*/





            holder.daytimeTextView = convertView.findViewById(R.id.daytime_text_view);
            holder.dishTextView = convertView.findViewById(R.id.dish_text_view);
            holder.componentTextView = convertView.findViewById(R.id.component_text_view);
            holder.priceTextView = convertView.findViewById(R.id.price_text_view);

            holder.iconImageView = convertView.findViewById(R.id.icon_image_view);
            holder.icon1ImageView = convertView.findViewById(R.id.icon_image_view1);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Dish dish = getItem(position);

            holder.daytimeTextView.setText(dish.getmDayTime());

            holder.dishTextView.setText(dish.getmDish());
            holder.componentTextView.setText(dish.getmComponent());
            double originalPrice = dish.getmPrice();
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
            String Price = format.format(originalPrice);

            //String format2 = new DecimalFormat("#,###.00").format(dish.getmPrice());
            holder.priceTextView.setText(Price);

            int icon_id = getIcon(dish.getmImageResId1());
             holder.iconImageView.setImageResource(icon_id);
            int icon_id2 = getIcon(dish.getmImageResId2());

            holder.icon1ImageView.setImageResource(icon_id2);

        Date dateObject  = new Date(dish.getmDate());

        int listCount = convertView.getHeight();
        Log.e("ListView Height", String.valueOf(listCount));
        return convertView;
    }
    static class ViewHolder {
        // The ViewHolder design pattern
        TextView daytimeTextView;
        TextView dishTextView;
        TextView componentTextView;
        TextView priceTextView;
        ImageView iconImageView;
        ImageView icon1ImageView;
    }
    private int getIcon(int iconId){
        int mIconId;
        switch (iconId){
            case 21:
                mIconId = R.drawable.international;
                break;
            case 31:
                mIconId = R.drawable.mensavital;
                break;
            case 41:
                mIconId = R.drawable.mensaregional;
                break;
            case 51:
                mIconId = R.drawable.odz;
                break;


                default:
                    mIconId = 0;


        }
        return mIconId;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault());
        //SimpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return  dateFormat.format(dateObject);


    }

    private static String formatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(date);


    }

}
