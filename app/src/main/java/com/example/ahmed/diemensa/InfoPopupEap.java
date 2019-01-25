package com.example.ahmed.diemensa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

public class InfoPopupEap extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_popup);

        Slidr.attach(this);
        ImageView popupImageView = findViewById(R.id.popup_text_view);
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);

        Intent intent = getIntent();

        String info = intent.getStringExtra("Detail");



        switch (info){
            case "eap":
                textView1.setText(getString(R.string.address_title_eap));
                textView2.setText(getString(R.string.address_info_eap));
                textView3.setText(getString(R.string.opening_hours_title_eap));
                textView4.setText(getString(R.string.opening_hours_info_eap));
                textView5.setText(getString(R.string.other_title_eap));
                textView6.setText(getString(R.string.other_info_eap));
                popupImageView.setImageResource(R.drawable.eapmensa);
                break;
            case "cz":
                textView1.setText(getString(R.string.address_title_cz));
                textView2.setText(getString(R.string.address_info_cz));
                textView3.setText(getString(R.string.opening_hours_title_cz));
                textView4.setText(getString(R.string.opening_hours_info_cz));
                textView5.setText(getString(R.string.other_title_cz));
                textView6.setText(getString(R.string.other_info_cz));
                popupImageView.setImageResource(R.drawable.czmensa);

        }
      /*  DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.9), (int) (height*.9));

        */

      /*  TableLayout tableLayout = findViewById(R.id.mensa_info_layout);
        tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/


    }
}
