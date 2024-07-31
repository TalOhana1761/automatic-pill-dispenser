package com.example.pillboxv3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static String deviceIP;
    public static int devicePort = 21334;
    public static String deviceEmail;
    public static String CMD = "3x";
    public int connectionFlag = 0;

    public timestampDatebase db;
    public TextView nextTimestamp;
    public List<timestamp> todayTimestamps;
    public String nextTsText;

    public String[] medicineNames;
    public String[] medicineNamesToConvert;
    public String med1,med2,med3,med4;
    public List<String> slotNumbersBeforeArray;
    public String[] slotNumbers;
    public String[] medNamesBeforeRemovingBlanks;
    public int otherMedCheck;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i1 = getIntent();
        prefs = getSharedPreferences("smartPillboxSharedPrefrences", Context.MODE_PRIVATE);
        //deviceIP = prefs.getString("ip","");
        deviceIP = "10.0.0.4";
        db = timestampDatebase.getInstance(MainActivity.this);

        med1 = prefs.getString("med1","");
        med2 = prefs.getString("med2","");
        med3 = prefs.getString("med3","");
        med4 = prefs.getString("med4","");
        medicineNamesToConvert = new String[]{med1,med2,med3,med4};
        slotNumbersBeforeArray = new ArrayList<String>();
        for(int i = 0; i < medicineNamesToConvert.length; i++)
        {
            if(!(medicineNamesToConvert[i].equals("")))
            {
                slotNumbersBeforeArray.add(String.valueOf(i+1));
            }
        }
        slotNumbersBeforeArray.removeAll(Arrays.asList("",null));
        slotNumbers = slotNumbersBeforeArray.toArray(new String[slotNumbersBeforeArray.size()]);
        List<String> medicineNamesList = new ArrayList<String>(Arrays.asList(med1,med2,med3,med4));
        medNamesBeforeRemovingBlanks = medicineNamesList.toArray(new String[medicineNamesList.size()]);
        medicineNamesList.removeAll(Arrays.asList("",null));
        medicineNames = medicineNamesList.toArray(new String[medicineNamesList.size()]);

        nextTimestamp = findViewById(R.id.nextTimestampDispense);
        displayNextTimestamp();

    }

    public void displayNextTimestamp()
    {
        new Thread(new Runnable() {
        @Override
        public void run() {
            Calendar now = Calendar.getInstance();
            int today = now.get(Calendar.DAY_OF_WEEK);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int hourToDisplay = 0;
            int minuteTodisplay = 0;
            todayTimestamps = db.getTimestampDAO().getTimestampsByDay(today);
            Log.d("nextDispenseCheck: " , "time now is: " + String.valueOf(hour) + minute);
            if(!todayTimestamps.isEmpty())
            {
                for(int i = 0; i < (todayTimestamps.size()); i++)
                {
                    if(todayTimestamps.get(i).getHour() >= hour)
                    {
                        hourToDisplay = todayTimestamps.get(i).getHour();
                        minuteTodisplay = todayTimestamps.get(i).getMinute();
                        break;
                    }
                }
                if(hourToDisplay != 0)
                {
                    nextTsText = "Next dispensing time is at " + String.valueOf(hourToDisplay) + ":" + String.valueOf(minuteTodisplay);
                    if(minuteTodisplay == 0)
                    {
                        nextTsText = "Next dispensing time is at " + String.valueOf(hourToDisplay) + ":" + "00";
                    }
                }
                else
                {
                    nextTsText = "No pill dispenses today";
                }
            }
            else
            {
                nextTsText = "No pill dispenses today";
            }
            nextTimestamp.setText(nextTsText);
        }
    }).start();
    }


    public void dispense(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose medications to dispense");
        builder.setMultiChoiceItems(medicineNames, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    otherMedCheck += Integer.parseInt(slotNumbers[which]) * (int)Math.pow(10,which);
                }
                else {
                    otherMedCheck -= Integer.parseInt(slotNumbers[which]) * (int)Math.pow(10,which);
                }
            }
        });
        builder.setPositiveButton("Dispense", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CMD = "3x" + String.valueOf(otherMedCheck);
                sendCMDtoRPi();
                Log.d("dispense" , "will send cmd: " +  CMD);
                otherMedCheck = 0;
            }
        });
        builder.create();
        if(!deviceIP.equals(""))
        {
            builder.show();
            Log.d("ip address" , "ip address is: " + deviceIP);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please enter device IP address", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendCMDtoRPi()
    {
        Log.d("check", "CMD function happened");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(deviceIP,devicePort);
                    PrintWriter dataToRPi = new PrintWriter(socket.getOutputStream());
                    dataToRPi.write(CMD);
                    dataToRPi.flush();
                    dataToRPi.close();
                    socket.close();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Medications dispensed successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (IOException e)
                {
                    connectionFlag = 0;
                }
            }
        }).start();
    }

    public void goToConfigurationPage(View v)
    {
        Intent confPage = new Intent(this, rpidetails.class);
        startActivity(confPage);
    }

    public void goToTimestampingPage(View v)
    {
        Intent dayPicking = new Intent(this, dayPicking.class);
        startActivity(dayPicking);
    }

}