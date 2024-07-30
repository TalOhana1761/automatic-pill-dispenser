package com.example.pillboxv3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;

public class timestampMarkingActivity extends AppCompatActivity {

    public TextView todayName;

    public timestamp timestampToAdd = new timestamp(0,1,0,0);;
    public timestamp timestampToAdd1 = new timestamp(0,1,0,0);
    public timestamp timestampToAdd2 = new timestamp(0,1,0,0);
    public timestamp timestampToAdd3 = new timestamp(0,1,0,0);
    public timestamp timestampToAdd4 = new timestamp(0,1,0,0);
    public timestamp timestampToAdd5 = new timestamp(0,1,0,0);
    public timestamp[] timestampsArray;

    public TextView medText1;
    public TextView medText2;
    public TextView medText3;
    public TextView medText4;
    public TextView medText5;

    public Button[] setButtons;
    public Button[] removeButtons;

    public String[] medicineNamesToConvert;
    public String[] medicineNames;
    public String med1;
    public String med2;
    public String med3;
    public String med4;
    public String[] slotNumbers;
    public timestampDatebase pillboxdatabase;
    public static int devicePort = 21334;
    public static String deviceIP;
    public String CMD;
    SharedPreferences prefs;
    public String noTimestamp;
    public List<timestamp> todaysTimestamps;
    public int todayNumber = 1;
    public String today;
    public TextView[] arrayOfTextviews;
    public int otherMedCheck;
    public List<String> slotNumbersBeforeArray;
    public String[] medNamesBeforeRemovingBlanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timestamp_marking);

        Intent i1 = getIntent();

        timestampsArray = new timestamp[] {timestampToAdd1, timestampToAdd2, timestampToAdd3 , timestampToAdd4, timestampToAdd5};

        Button add1 = findViewById(R.id.Add);
        Button add2 = findViewById(R.id.add1);
        Button add3 = findViewById(R.id.add2);
        Button add4 = findViewById(R.id.add3);
        Button add5 = findViewById(R.id.add4);
        setButtons = new Button[] {add1,add2,add3,add4,add5};

        Button remove1 = findViewById(R.id.remove);
        Button remove2 = findViewById(R.id.remove1);
        Button remove3 = findViewById(R.id.remove2);
        Button remove4 = findViewById(R.id.remove3);
        Button remove5 = findViewById(R.id.remove4);
        removeButtons = new Button[] {remove1,remove2,remove3,remove4,remove5};

        todayName = (TextView)findViewById(R.id.dayName);
        today = i1.getStringExtra("todaysName");
        todayName.setText(today);

        prefs = getSharedPreferences("smartPillboxSharedPrefrences", Context.MODE_PRIVATE);
        //deviceIP = "10.0.0.4"; this is the actual rpi ip address.
        deviceIP = prefs.getString("ip",null);
        pillboxdatabase = timestampDatebase.getInstance(timestampMarkingActivity.this);
        medText1 = (TextView)findViewById(R.id.ts);
        medText2 = (TextView)findViewById(R.id.ts1);
        medText3 = (TextView)findViewById(R.id.ts2);
        medText4 = (TextView)findViewById(R.id.ts3);
        medText5 = (TextView)findViewById(R.id.ts4);
        arrayOfTextviews = new TextView[]{medText1, medText2,medText3, medText4 ,medText5};

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

        noTimestamp = "-----";

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimestamp(0);
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimestamp(1);
            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimestamp(2);
            }
        });
        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimestamp(3);
            }
        });
        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimestamp(4);
            }
        });

        remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTimeStamp(1);
            }
        });
        remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTimeStamp(2);
            }
        });
        remove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTimeStamp(3);
            }
        });
        remove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTimeStamp(4);
            }
        });
        remove5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTimeStamp(5);
            }
        });

        setInitialTimestampsOnCreate();
    }

    public void goBackToLastIntent(View v)
    {
        Intent i1 = new Intent(this, dayPicking.class);
        startActivity(i1);
    }

    public void addTimestamp(int numberOfTimeStamp)
    {
        TimePickerDialog tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                timestampsArray[numberOfTimeStamp].setDay(todayNumber);
                timestampsArray[numberOfTimeStamp].setHour(hourOfDay);
                Log.d("check " , String.valueOf(timestampsArray[numberOfTimeStamp].getMinute()));
                timestampsArray[numberOfTimeStamp].setMinute(minute);
                String toDisplay = timestampsArray[numberOfTimeStamp].getHour() + ":" + timestampsArray[numberOfTimeStamp].getMinute() + getTodaysMed(timestampsArray[numberOfTimeStamp].getMedication());
                removeButtons[numberOfTimeStamp].setVisibility(View.VISIBLE);
                setButtons[numberOfTimeStamp].setVisibility(View.GONE);
                arrayOfTextviews[numberOfTimeStamp].setText(toDisplay);
                Log.d("he" , "this happened1");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pillboxdatabase.getTimestampDAO().addTimeStamp(timestampsArray[numberOfTimeStamp]);
                        timestampsArray[numberOfTimeStamp] = pillboxdatabase.getTimestampDAO().getAllTimestamps().get(pillboxdatabase.getTimestampDAO().getAllTimestamps().size()-1);
                        CMD = "1" + "x" + timestampsArray[numberOfTimeStamp].getDay() + "x" + timestampsArray[numberOfTimeStamp].getHour() + "x" + timestampsArray[numberOfTimeStamp].getMinute() + "x" + timestampsArray[numberOfTimeStamp].getMedication();
                        Log.d("he" , "this happened2");
                        try {
                            Socket socket = new Socket(deviceIP,devicePort);
                            PrintWriter dataToRPi = new PrintWriter(socket.getOutputStream());
                            dataToRPi.write(CMD);
                            dataToRPi.flush();
                            dataToRPi.close();
                            socket.close();
                        }catch (IOException e){e.printStackTrace();}
                    }
                }).start();

                Toast.makeText(timestampMarkingActivity.this, "Timestamp added!" , Toast.LENGTH_SHORT).show();
            }
        }, 15, 0, true);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose medications to dispense");
        builder.setMultiChoiceItems(medicineNames, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    otherMedCheck += Integer.parseInt(slotNumbers[which]) * (int)Math.pow(10,which);
//                    Log.d("check timestampMedPickingDialog" , "which is: " + String.valueOf(which));
//                    Log.d("check timestampMedPickingDialog" , "isChecked is: " + String.valueOf(isChecked));
//                    Log.d("check timestampMedPickingDialog" , "otherMedCheck is: " + String.valueOf(otherMedCheck));
                }
                else {
                    otherMedCheck -= Integer.parseInt(slotNumbers[which]) * (int)Math.pow(10,which);
                }
            }
        });
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timestampsArray[numberOfTimeStamp].setMedication(otherMedCheck);
                otherMedCheck = 0;
                tp.show();
            }
        });
        builder.create();
        builder.show();
    }

    public void removeTimeStamp(int tsNumber)
    {
        setButtons[tsNumber - 1].setVisibility(View.VISIBLE);
        removeButtons[tsNumber - 1].setVisibility(View.GONE);
        arrayOfTextviews[tsNumber - 1].setText(noTimestamp);
        Toast.makeText(timestampMarkingActivity.this,"Timestamp removed." , Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                pillboxdatabase.getTimestampDAO().deleteTimeStamp(timestampsArray[tsNumber-1]);
//                Log.d("check timestampRemove" , String.valueOf(timestampsArray[tsNumber-1].getId()));
//                Log.d("check timestampRemove" , String.valueOf(timestampsArray[tsNumber-1].getMedication()));
//                Log.d("check timestampRemove" , String.valueOf(timestampsArray[tsNumber-1].getHour()));
//                Log.d("check timestampRemove" , String.valueOf(timestampsArray[tsNumber-1].getMinute()));
                CMD = "2" + "x" + timestampsArray[tsNumber-1].getDay() + "x" + timestampsArray[tsNumber-1].getHour() + "x" + timestampsArray[tsNumber-1].getMinute() + "x" + timestampsArray[tsNumber-1].getMedication();
                try {
                    Socket socket = new Socket(deviceIP,devicePort);
                    PrintWriter dataToRPi = new PrintWriter(socket.getOutputStream());
                    dataToRPi.write(CMD);
                    dataToRPi.flush();
                    dataToRPi.close();
                    socket.close();
                }catch (IOException e){e.printStackTrace();}
            }
        }).start();
    }

    public void setInitialTimestampsOnCreate()
    {

        switch (today)
        {
            case "Sunday":
                todayNumber = 1;
                break;
            case "Monday":
                todayNumber = 2;
                break;
            case "Tuesday":
                todayNumber = 3;
                break;
            case "Wednsday":
                todayNumber = 4;
                break;
            case "Thursday":
                todayNumber = 5;
                break;
            case "Friday":
                todayNumber = 6;
                break;
            case "Saturday":
                todayNumber = 7;
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                todaysTimestamps = pillboxdatabase.getTimestampDAO().getTimestampsByDay(todayNumber);
                if (todaysTimestamps.size() != 0) {
                    int size = arrayOfTextviews.length - 1;
                    if (todaysTimestamps.size() < size) {
                        size = todaysTimestamps.size();
                    }
                    for (int i = 0; i < (size); i++) {
                        String hour = String.valueOf(todaysTimestamps.get(i).getHour());
                        String minute = String.valueOf(todaysTimestamps.get(i).getMinute());
                        if(minute.equals("0"))
                        {
                            minute = "00";
                        }
                        String meds = getTodaysMed(todaysTimestamps.get(i).getMedication());
                        String combined = hour + ":" + minute + meds;
                        timestampsArray[i] = todaysTimestamps.get(i);
                        arrayOfTextviews[i].setText(combined);
                        setButtons[i].setVisibility(View.GONE);
                        removeButtons[i].setVisibility(View.VISIBLE);
                    }
                }
            }
        }).start();
    }

    public String getTodaysMed(int givenMed)
    {
        int index = 0;
        String finalMedString = "";
        while(givenMed > 0)
        {
            if(givenMed%10 != 0 && slotNumbersBeforeArray.contains(String.valueOf(givenMed%10)))
            {
//                Log.d("check medNameProcess" , "medName is: " + medicineNames[index]);
//                Log.d("check medNameProcess","index is: " + String.valueOf(index) + "givenMedModulu is: " + String.valueOf(givenMed%10));
//                Log.d("check medNameProcess" , "med string is: " + finalMedString);
                finalMedString += " " + medicineNames[(givenMed%10) - 1];
                index++;
            }
            givenMed = givenMed / 10;
        }
        return finalMedString;
    }

}