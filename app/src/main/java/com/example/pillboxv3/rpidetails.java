package com.example.pillboxv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class rpidetails extends AppCompatActivity {

    SharedPreferences prefs;
    TextView ipTV;
    TextView emailTV;
    TextView volumeTV;
    TextView notTV;
    TextView slot1TV;
    TextView slot2TV;
    TextView slot3TV;
    TextView slot4TV;
    public static int devicePort = 21334;
    public String CMD;
    public int connectionFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpidetails);

        ipTV = (TextView)findViewById(R.id.deviceIP);
        emailTV = (TextView)findViewById(R.id.deviceEmail);
        volumeTV = (TextView)findViewById(R.id.audioVolume);
        notTV = (TextView)findViewById(R.id.notificationTime);
        slot1TV = (TextView)findViewById(R.id.med1);
        slot2TV = (TextView)findViewById(R.id.med2);
        slot3TV = (TextView)findViewById(R.id.med3);
        slot4TV = (TextView)findViewById(R.id.med4);

        prefs = getSharedPreferences("smartPillboxSharedPrefrences", Context.MODE_PRIVATE);

        if(prefs.getString("ip",null) != null)
        {
            ipTV.setText(prefs.getString("ip",null));
        }
        if(prefs.getString("email",null) != null)
        {
            emailTV.setText(prefs.getString("email",null));
        }
        if(prefs.getString("volume",null) != null)
        {
            volumeTV.setText(prefs.getString("volume",null));
        }
        if(prefs.getString("notification",null) != null)
        {
            notTV.setText(prefs.getString("notification",null));
        }
        if(prefs.getString("med1",null) != null)
        {
            slot1TV.setText(prefs.getString("med1",null));
        }
        if(prefs.getString("med2",null) != null)
        {
            slot2TV.setText(prefs.getString("med2",null));
        }
        if(prefs.getString("med3",null) != null)
        {
            slot3TV.setText(prefs.getString("med3",null));
        }
        if(prefs.getString("med4",null) != null)
        {
            slot4TV.setText(prefs.getString("med4",null));
        }

    }

    public void goBackToMain(View v)
    {
        transmitSettingsToServer();

        if(connectionFlag == 1)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ip" , ipTV.getText().toString());
            editor.putString("email" , emailTV.getText().toString());
            editor.putString("volume" , volumeTV.getText().toString());
            editor.putString("notification" , notTV.getText().toString());
            editor.putString("med1" , slot1TV.getText().toString());
            editor.putString("med2" , slot2TV.getText().toString());
            editor.putString("med3" , slot3TV.getText().toString());
            editor.putString("med4" , slot4TV.getText().toString());
            editor.apply();

            Toast.makeText(rpidetails.this,"Changes Saved",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(rpidetails.this,"Device is offline, changes were not saved.",Toast.LENGTH_SHORT).show();
        }
        Intent back = new Intent(this, MainActivity.class);

        startActivity(back);
    }


    public void transmitSettingsToServer()
    {
        CMD = "4!" + emailTV.getText().toString() + "!" + volumeTV.getText().toString() + "!" + notTV.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(ipTV.getText().toString(),devicePort);
                    PrintWriter dataToRPi = new PrintWriter(socket.getOutputStream());
                    dataToRPi.write(CMD);
                    dataToRPi.flush();
                    dataToRPi.close();
                    socket.close();
                    connectionFlag = 1;
                }catch (IOException e)
                {
                    connectionFlag = 0;
                    e.printStackTrace();
                }
            }
        }).start();
    }
}