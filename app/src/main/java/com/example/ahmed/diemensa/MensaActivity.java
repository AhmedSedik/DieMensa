package com.example.ahmed.diemensa;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *
 */
public class MensaActivity extends AppCompatActivity {

    private Bundle extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensa);

        final ArrayList<Mensa> mensas = new ArrayList<>();

        mensas.add(new Mensa(getString(R.string.mensa_eap),R.drawable.eapmensa));
        mensas.add(new Mensa(getString(R.string.mensa_cz),R.drawable.czmensa));
        mensas.add(new Mensa(getString(R.string.mensa_philo),R.drawable.philomensa));
        mensas.add(new Mensa(getString(R.string.mensa_hg),R.drawable.hgmensa));
        mensas.add(new Mensa(getString(R.string.mensa_eah),R.drawable.eahmensa));
        mensas.add(new Mensa(getString(R.string.mensa_vgt),R.drawable.vgtmensa));

        MensaAdapter adapter = new MensaAdapter(this,mensas);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Mensa mensa = mensas.get(i);

            switch (i){
                case 0:
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com"));
                    startActivity(urlIntent);
                    Log.v("First Item: ", "Pressed");

                    break;
                case 1 :
                     Intent infoMensaIntent = new Intent(MensaActivity.this,MondayDishAvtivity.class);
                        startActivity(infoMensaIntent);
                    Log.v("second Item: ", "Pressed");
            }
        }
    });

        /*ImageView mensaInfoIcon = (ImageView)findViewById(R.id.info_mensa_image_view);


        mensaInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MensaActivity.this,InfoPopupEap.class));
            }
        });

         button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MensaActivity.this,InfoPopupEap.class);
                startActivity(intent);
            }
        });

*/
    }
}
