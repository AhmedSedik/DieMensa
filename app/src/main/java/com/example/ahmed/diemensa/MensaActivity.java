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

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        //TODO check the current day and for the right Activity on that day
            switch (position){
                case 0:

                    Log.v("First Item: ", "Pressed");
                    Toast.makeText(MensaActivity.this, "First Items Pressed", Toast.LENGTH_SHORT).show();
                    getDayOfWeek(position);
                   // intent.putExtra("Place",classes[position]);


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
    private void getDayOfWeek(int position){
        //am starting to get date to populate in spinner
        //here am getting the weekday so i can send information based upon
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        Calendar calendar = Calendar.getInstance();//today
        Toast.makeText(MensaActivity.this, "Today is "+ dayNames[calendar.get(Calendar.DAY_OF_WEEK)], Toast.LENGTH_SHORT).show();
        System.out.println("Today is a "
                + dayNames[calendar.get(Calendar.DAY_OF_WEEK)]);
        String dayOfTheWeek = dayNames[calendar.get(Calendar.DAY_OF_WEEK)];



        Date date = new Date();
        Calendar calendarToday = Calendar.getInstance();//today
        calendarToday.setTime(date);
        date = calendarToday.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy",Locale.getDefault());
        calendarToday.add(Calendar.DATE,0);
        String montagDate = dateFormat.format(date);


        //sending which day of the week we are in as package
        Intent intent = new Intent(MensaActivity.this,MondayActivity.class);
        intent.putExtra("Branch",classes[position]);
        intent.putExtra("Saturday",dayOfTheWeek);
        startActivity(intent);

        /*if(dayOfTheWeek.equals("Saturday")){
            Intent intent = new Intent(MensaActivity.this,MondayActivity.class);
            intent.putExtra("Branch",classes[position]);
            intent.putExtra("Saturday","Saturday");
            startActivity(intent);
        }
*/



        //End
    }
}
