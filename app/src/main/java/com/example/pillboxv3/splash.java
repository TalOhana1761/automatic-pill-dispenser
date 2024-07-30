package com.example.pillboxv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent i = new Intent(this,MainActivity.class);
        //getActionBar().hide();
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(i);
            }
        };
        h.postDelayed(r,400);
    }
}