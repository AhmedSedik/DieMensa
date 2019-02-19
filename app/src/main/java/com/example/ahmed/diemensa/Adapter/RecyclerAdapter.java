package com.example.ahmed.diemensa.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.diemensa.Interfaces.OnItemClickListener;
import com.example.ahmed.diemensa.Model.Dish;
import com.example.ahmed.diemensa.R;
import com.example.ahmed.diemensa.Utils.SharedPreference;

import java.util.List;
import java.util.Locale;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHoler> {
    private Context mContext;
    private List<Dish> dishes;
    private OnItemClickListener mListener;
    public SharedPreference sharedPreference;




    public RecyclerAdapter(Context context, List<Dish> dishes) {
        mContext = context;
        this.dishes = dishes;

        sharedPreference = new SharedPreference();


    }


    @NonNull
    @Override
    public RecyclerViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_dish, viewGroup, false);
        return new RecyclerViewHoler(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHoler holder, int position) {

        Dish dish = dishes.get(holder.getAdapterPosition());
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

            holder.heart.setImageResource(R.drawable.heart_red);
            holder.heart.setTag("red");
        } else {
            holder.heart.setImageResource(R.drawable.heart_grey);
            holder.heart.setTag("grey");

        }


        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dishes = new ArrayList<>();
                //dishes =sharedPreference.getFavorites(mContext);
                if (mListener != null) {
                    int pos = holder.getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        String tag = holder.heart.getTag().toString();

                        if (tag.equalsIgnoreCase("grey")) {
                            mListener.onLikedButton(pos);

                            //Toast.makeText(mContext, mContext.getResources().getString(R.string.add_favr), Toast.LENGTH_SHORT).show();
                            holder.heart.setTag("red");
                            holder.heart.setImageResource(R.drawable.heart_red);
                            sharedPreference.addFavorite(mContext.getApplicationContext(), dishes.get(pos));


                        } else {
                            mListener.onUnlikedButton(pos);
                            sharedPreference.removeFavorite(mContext, dishes.get(pos));
                            holder.heart.setTag("grey");
                            holder.heart.setImageResource(R.drawable.heart_grey);
                            //Toast.makeText(mContext, mContext.getResources().getString(R.string.remove_favr), Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            }
        });
        /*for (int i = 0; i < size; i++) {

            if (aBoolean&&pos==position) {
                holder.favourited.setColorFilter(ContextCompat.getColor(mContext,
                        R.color.colorOrange));
                holder.favourited.setTag("filled");
            } else {
                holder.favourited.clearColorFilter();
                holder.favourited.setTag("empty");

            }
        }*/

        /*If a product exists in SQLite then set filled star drawable and set a tag*/
       /* if(checkFavouriteItem(dish)){

            holder.favourited.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.colorOrange));
            holder.favourited.setTag("filled");

        }else{

            holder.favourited.clearColorFilter();
            holder.favourited.setTag("empty");

        }*/

    }


    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class RecyclerViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView daytimeTextView;
        private TextView dishTextView;
        private TextView componentTextView;
        private TextView priceTextView;
        private TextView likesTextView;
        private ImageView iconImageView;
        private ImageView icon1ImageView;
        private ImageView heart;


        public RecyclerViewHoler(@NonNull View itemView) {
            super(itemView);
            this.daytimeTextView = itemView.findViewById(R.id.daytime_text_view);
            this.dishTextView = itemView.findViewById(R.id.dish_text_view);
            this.componentTextView = itemView.findViewById(R.id.component_text_view);
            this.priceTextView = itemView.findViewById(R.id.price_text_view);
            this.iconImageView = itemView.findViewById(R.id.icon_image_view);
            this.icon1ImageView = itemView.findViewById(R.id.icon_image_view1);
            this.likesTextView = itemView.findViewById(R.id.likes_counter_text_view);
            //final String likes = String.valueOf(likesTextView.getText());
            //final int likess = Integer.parseInt(likes);

            this.heart = itemView.findViewById(R.id.imgbtn_favorite);


            itemView.setOnClickListener(this);


            //aBoolean = true;

        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(view, position);
                }
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static int getIcon(int iconId) {
        int mIconId;
        switch (iconId) {
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


    /*Checks whether a particular product exists in SharedPreferences*/
    public  boolean checkFavoriteItem(Dish checkProduct) {
        boolean check = false;
        List<Dish> favorites = sharedPreference.getFavorites(mContext);
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

    public boolean checkFavouriteItem(Dish checkStarredItem) {
        boolean check = false;
        //shared preference way
        List<Dish> favouriteItemsInSharedPreference = sharedPreference.getFavorites(mContext);

        if (favouriteItemsInSharedPreference != null) {
            for (Dish word : favouriteItemsInSharedPreference) {
                if (word.equals(checkStarredItem)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

}