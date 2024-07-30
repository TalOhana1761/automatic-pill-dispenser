package com.example.pillboxv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dayPicking extends AppCompatActivity {

    public Button sunday;
    public Button monday;
    public Button tuesday;
    public Button wednsday;
    public Button thursday;
    public Button friday;
    public Button saturday;
    public Button back;
    public String[] dayNames = {"Sunday" , "Monday" , "Tuesday" , "Wednsday" , "Thursday" , "Friday" , "Saturday"};
    public String dayNameToPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_picking);

        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednsday = findViewById(R.id.wednsday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);

        back = findViewById(R.id.back);

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTimestamping(1);
            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTimestamping(2);
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTimestamping(3);
            }
        });

        wednsday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTimestamping(4);
            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTimestamping(5);
            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTimestamping(6);
            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTimestamping(7);
            }
        });


    }


    public void proceedToTimestamping(int day)
    {
        Intent i = new Intent(this,timestampMarkingActivity.class);
        i.putExtra("todaysName" , dayNames[day - 1]);
        startActivity(i);
    }

    public void goBackToMain(View v)
    {
        Intent mainPage = new Intent(this, MainActivity.class);
        startActivity(mainPage);
    }
}