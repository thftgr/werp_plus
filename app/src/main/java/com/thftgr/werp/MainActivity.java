package com.thftgr.werp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public Button startButton;
    public EditText refid;
    public TextView data;
    public int dataCount = 0;
    public boolean running = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.start);
        refid = findViewById(R.id.reff);
        data = findViewById(R.id.createdData);
        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        String prefData = pref.getString("ID","");
        refid.setText(prefData);
    }

    public void start_Button_clicked(View view) {
        //7e2757b7-badc-4303-8cd0-2eefa9d78e3b
        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ID", refid.getText().toString());
        editor.apply();

        if (running) {
            running = false;
        } else {
            running = true;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (running) {


                        if (new Werp().generator(refid.getText().toString())) {
                            dataCount += 1;
                            data.setText(dataCount + "GB");
                        } else {
                            data.setText("fail | " + dataCount + "GB");
                        }
                        String tmp = data.getText().toString();
                        for (int i = 20; i > 0; i--) {
                            if(!running){
                                data.setText("pause. : " + tmp);
                                break;
                            }
                            Thread.sleep(1000);
                            data.setText(i + "sec... : " + tmp);
                        }


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());

                }
                running = false;

            }
        }).start();
    }
}