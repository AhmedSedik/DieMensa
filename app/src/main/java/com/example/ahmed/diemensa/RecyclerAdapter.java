package com.example.ahmed.diemensa;


import android.content.Context;
import android.icu.text.NumberFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.ahmed.diemensa.MondayActivity.LOG_TAG;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Dish> dishes;
    private Context context;

    public RecyclerAdapter(Context context, List<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_dish, parent, false);

        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Dish dish = dishes.get(position);
        //Toast.makeText(context, "pos:" + position, Toast.LENGTH_SHORT).show();
        holder.daytimeTextView.setText(dish.getmDayTime());

        holder.dishTextView.setText(dish.getmDish());
        holder.componentTextView.setText(dish.getmComponent());
        double originalPrice = dish.getmPrice();
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        String price = format.format(originalPrice);

        //String format2 = new DecimalFormat("#,###.00").format(dish.getmPrice());
        holder.priceTextView.setText(price);

        int icon_id = getIcon(dish.getmImageResId1());
        holder.iconImageView.setImageResource(icon_id);
        int icon_id2 = getIcon(dish.getmImageResId2());
        holder.icon1ImageView.setImageResource(icon_id2);
        holder.likesTextView.setText(dish.getmLikes());


        holder.mRatingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //String counter = String.valueOf(rating);
                String counter = "2";
                    Like likes = new Like(counter);


                try {
                    JSONObject jsonObject =  new JSONObject();
                    //JSONArray jsonArray = new JSONArray();

                    jsonObject.put("likes",likes.getLikes());
                    //jsonArray.put(jsonObject);

                    Log.d("json api", "Json array converted from Product: " + jsonObject.toString());
                    String jsonData = String.valueOf(jsonObject);

                    new UpdateLikes().execute(jsonData);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
                    e.printStackTrace();
                }
                Log.e(MondayActivity.LOG_TAG, "Rating is:" + rating);
            }
        });
    }

    public void setData(List<Dish> data) {
        dishes.clear();
        dishes.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.i("DishAdapter Mensa ", "dishes.size() = " + dishes.size());
        return dishes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // The ViewHolder design pattern
        private TextView daytimeTextView;
        private TextView dishTextView;
        private TextView componentTextView;
        private TextView priceTextView;
        private ImageView iconImageView;
        private ImageView icon1ImageView;
        private RatingBar mRatingbar;
        private TextView likesTextView;


        public ViewHolder(View view) {
            super(view);
            this.daytimeTextView = view.findViewById(R.id.daytime_text_view);
            this.dishTextView = view.findViewById(R.id.dish_text_view);
            this.componentTextView = view.findViewById(R.id.component_text_view);
            this.priceTextView = view.findViewById(R.id.price_text_view);
            this.iconImageView = view.findViewById(R.id.icon_image_view);
            this.icon1ImageView = view.findViewById(R.id.icon_image_view1);
            this.mRatingbar = view.findViewById(R.id.ratingBar);
            this.likesTextView = view.findViewById(R.id.likes_counter_text_view);
        }
    }

    private int getIcon(int iconId) {
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

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        //SimpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return dateFormat.format(dateObject);


    }

    private static String formatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(date);


    }


}

class UpdateLikes extends AsyncTask<String, Void, Void> {
    String URL = "http://192.168.178.22:81/likes.php";

    @Override
    protected Void doInBackground(String... params) {
        String jsonData = params[0];
        //String jsonData = "5";
        String charset = "UTF-8";
        try {
            java.net.URL url = new URL(URL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept-Charset", charset);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches (false);
            httpURLConnection.connect();

            //send data
            DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
            os.writeBytes(jsonData.toString());

            //OutputStream dos = httpURLConnection.getOutputStream();
            //dos.write(Integer.parseInt(jsonData.toString()));

            //receive & read data response
            InputStream is = httpURLConnection.getInputStream();
            StringBuilder result = new StringBuilder();
            int byteCharacter;
            while ((byteCharacter = is.read()) != -1) {
                result.append((char) byteCharacter);
            }
            Log.d("json api", "DoUpdateProduct.doInBackground Json return: " + result);
            os.flush();
            is.close();
            os.close();
            //httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
/*
  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = insertTextView.getText().toString();


                Like note = new Like(title);

                likesRef.add(note).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FirebaseError", e.toString());
                    }
                });
            }
        });

 */
//int likes = (int) holder.mRatingbar.getRating();
//holder.likesTextView.setText(String.valueOf(rating));
//JSON

            /*  Like dishes = new Like((int) rating);


                try {
                    JSONObject jsonObject =  new JSONObject();
                    JSONArray jsonArray = new JSONArray();

                    jsonObject.put("likes",dishes.mLikes);
                    jsonArray.put(jsonObject);

                    Log.d("json api", "Json array converted from Product: " + jsonArray.toString());
                    String jsonData = String.valueOf(jsonArray);

                    new UpdateLikes().execute(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

//TODO Override the onCreate ViewHolder
    /*

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.


        ViewHolder holder;

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_dish,
                    parent,false);
            holder = new ViewHolder(convertView);

                if(position %2 == 1){
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.font_green));
                    notifyDataSetChanged();
                } else {
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.background));
                    notifyDataSetChanged();
                }


            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Dish dish = dishes.get(position);

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

        return convertView;
    }
*/