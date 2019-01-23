package com.example.ahmed.diemensa;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *
 */
public class MensaActivity extends AppCompatActivity {

    public final String classes[] = {"eap", "cz",
            "philo", "hg", "eah", "vgt"};
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
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            Mensa mensa = mensas.get(position);

            switch (position){
                case 0:
                    Log.v("First Item: ", "Pressed");
                    Toast.makeText(MensaActivity.this, "First Items Pressed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MensaActivity.this,MondayActivity.class);
                    intent.putExtra("Branch",classes[position]);
                   // intent.putExtra("Place",classes[position]);
                    startActivity(intent);

                    Log.v("First Item: ", "Pressed");

                    break;
                case 1 :
                    Intent infoMensaIntent = new Intent(MensaActivity.this,MondayActivity.class);

                    Log.v("second Item: ", "Pressed");
                    infoMensaIntent.putExtra("Branch",classes[position]);
                    infoMensaIntent.putExtra("Place",classes[position]);
                    startActivity(infoMensaIntent);
                    break;
                case 2:
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com"));
                    startActivity(urlIntent);
                    Toast.makeText(MensaActivity.this, "Third Items Pressed", Toast.LENGTH_SHORT).show();

                    break;
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
