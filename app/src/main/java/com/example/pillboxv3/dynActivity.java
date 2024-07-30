package com.example.pillboxv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class dynActivity extends AppCompatActivity {

    Button back;
    Button b1;
    Button b2;
    LinearLayout l1;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyn);

//        back = findViewById(R.id.back);
//        Button b1 = findViewById(R.id.btn1);
//        b2 = findViewById(R.id.btn2);
//
//        l1 = findViewById(R.id.horizLayout);
//        txt = findViewById(R.id.txt1);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            int index = 1;
//            @Override
//            public void onClick(View v) {
//                createButton(index,params,l1);
//                index++;
//            }
//        });

        LinearLayout ll = (LinearLayout)findViewById(R.id.layout); //Screen layout
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        final Button newItemButton = findViewById(R.id.newItemButton);
        newItemButton.setText("Create new button");

        newItemButton.setOnClickListener(new View.OnClickListener() {
            int pressCount = 1; //Count how many times button was pressed
            public void onClick(View v) {

                newItemButton.setText("Button Clicked: "+pressCount);
                createButton(pressCount, params, ll); //Click to create new button
                pressCount++;
            }
        });

    }

//    public void createButton(int id, LinearLayout.LayoutParams inputParams, LinearLayout inputLL)
//    {
//        Button newBtn = new Button(this);
//        newBtn.setId(id);
//        String hi = "hiThere";
//        newBtn.setText("hi");
//        //newBtn.setLayoutParams(b1.getLayoutParams());
//        inputLL.addView(newBtn,inputParams);
//    }

    public void createButton(int id, LinearLayout.LayoutParams inputParams, LinearLayout inputLL) {
        Button outButton = new Button(this);
        outButton.setId(id);
        final int id_ = outButton.getId();
        outButton.setText("Button " + id_);
        inputLL.addView(outButton, inputParams);
    }

    public void goBackToMain(View v)
    {
        Intent mainPage = new Intent(this, MainActivity.class);
        startActivity(mainPage);
    }

}