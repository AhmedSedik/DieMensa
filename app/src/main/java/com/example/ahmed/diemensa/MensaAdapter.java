package com.example.ahmed.diemensa;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MensaAdapter extends ArrayAdapter<Mensa> {


    public MensaAdapter(Activity context, ArrayList<Mensa> food) {
        super(context, 0, food);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_mensa, parent, false);
        }
        final Mensa myFood = getItem(position);

        final TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(myFood.getmTitle());

        ImageView mensaImage = listItemView.findViewById(R.id.mensa_image_view);
        mensaImage.setImageResource(myFood.getmResId());
        mensaImage.setVisibility(View.VISIBLE);

        listItemView.setTag(convertView);
        listItemView.setTag(position);

        final String classes[] = {"eap", "cz",
                "philo", "hg", "eah", "vgt"};
        ImageView mensaInfoIcon = listItemView.findViewById(R.id.info_mensa_image_view);
        mensaInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //View tempView = (View) button.getTag();


                Intent intent = new Intent(getContext(), InfoPopupEap.class);
                intent.putExtra("Detail", classes[position]);

                getContext().startActivity(intent);
                Log.v("Position: ", String.valueOf(myFood));
                Log.v("Array Position: ", classes[position]);

            }
        });
        return listItemView;
    }
}
