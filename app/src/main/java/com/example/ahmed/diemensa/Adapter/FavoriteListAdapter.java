package com.example.ahmed.diemensa.Adapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.diemensa.Interfaces.OnItemClickListener;
import com.example.ahmed.diemensa.Model.Dish;
import com.example.ahmed.diemensa.R;
import com.example.ahmed.diemensa.Utils.SharedPreference;

import java.util.List;
import java.util.Locale;

import static com.example.ahmed.diemensa.Adapter.RecyclerAdapter.getIcon;

public class FavoriteListAdapter extends ArrayAdapter<Dish> {

    private OnItemClickListener mListener;

    private Context context;
    List<Dish> dishList;
    SharedPreference sharedPreference;

    public FavoriteListAdapter(Context context, List<Dish> dishes) {
        super(context, R.layout.list_item_dish, dishes);
        this.context = context;
        this.dishList = dishes;
        sharedPreference = new SharedPreference();
    }

    private class ViewHolder {
         TextView daytimeTextView;
         TextView dishTextView;
         TextView componentTextView;
         TextView priceTextView;
         TextView likesTextView;
         ImageView iconImageView;
         ImageView icon1ImageView;
         ImageView heart;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Dish getItem(int position) {
        return dishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_fav, null);
            holder = new ViewHolder();
            holder.daytimeTextView = convertView.findViewById(R.id.daytime_text_view);
            holder.dishTextView = convertView.findViewById(R.id.dish_text_view);
            holder.componentTextView = convertView.findViewById(R.id.component_text_view);
            holder.priceTextView = convertView.findViewById(R.id.price_text_view);
            holder.iconImageView = convertView.findViewById(R.id.icon_image_view);
            holder.icon1ImageView = convertView.findViewById(R.id.icon_image_view1);
            holder.likesTextView = convertView.findViewById(R.id.likes_counter_text_view);

            holder.heart = convertView.findViewById(R.id.imgbtn_favorite);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Dish dish = (Dish) getItem(position);
        holder.daytimeTextView.setText(dish.getDaytime());
        holder.dishTextView.setText(dish.getDish());
        holder.componentTextView.setText(dish.getComponent());
        holder.likesTextView.setText(Integer.toString(dish.getLikes()));
        double originalPrice = dish.getPrice();
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        String price = format.format(originalPrice);

        //String format2 = new DecimalFormat("#,###.00").format(dish.getPrice());
        holder.priceTextView.setText(price);
        int icon_id = getIcon(dish.getIconid_1());
        holder.iconImageView.setImageResource(icon_id);
        int icon_id2 = getIcon(dish.getIconid_2());
        holder.icon1ImageView.setImageResource(icon_id2);

        /*If a product exists in shared preferences then set heart_red drawable
         * and set a tag*/
        if (checkFavoriteItem(dish)) {
            mListener.onLikedButton(position);
            holder.heart.setImageResource(R.drawable.heart_red);
            holder.heart.setTag("red");
        } else {
            mListener.onUnlikedButton(position);
            holder.heart.setImageResource(R.drawable.heart_grey);
            holder.heart.setTag("grey");
        }
        ViewHolder finalHolder = holder;
        holder.heart.setOnClickListener(view -> {
            ImageView button = view
                    .findViewById(R.id.imgbtn_favorite);

            dishList =sharedPreference.getFavorites(context);

            String tag = finalHolder.heart.getTag().toString();
            if (tag.equalsIgnoreCase("grey")) {

                sharedPreference.addFavorite(context,
                        dishList.get(position));
                Toast.makeText(
                        context,
                        context.getResources().getString(
                                R.string.add_favr),
                        Toast.LENGTH_SHORT).show();

                finalHolder.heart.setTag("red");
                finalHolder.heart.setImageResource(R.drawable.heart_red);
            } else {
                //mListener.onUnlikedButton(position);
                mListener.onUnlikedButton(getPosition(dish));
                sharedPreference.removeFavorite(context,
                        dishList.get(position));
                finalHolder.heart.setTag("grey");
                finalHolder.heart.setImageResource(R.drawable.heart_grey);
                dishList.remove(getItem(position));
                notifyDataSetChanged();

                Toast.makeText(
                        context,
                        context.getResources().getString(
                                R.string.remove_favr),
                        Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(Dish checkProduct) {
        boolean check = false;
        List<Dish> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (Dish product : favorites) {
                if (product.equals(checkProduct)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void add(Dish product) {
        super.add(product);
        dishList.add(product);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Dish product) {
        super.remove(product);
        dishList.remove(product);
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}