package com.example.clockapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class Activity2 extends AppCompatActivity {
    Chronometer chronometer;
    ImageButton btStart,btStop;
    private Toolbar toolbar;

    private boolean isResume;
    Handler handler;
    long tMilliSec,tStart,tBuff,tUpdate =0L;
    int sec, min, millisec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        toolbar = findViewById(R.id.toolBar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("StopWatch");



        chronometer = findViewById(R.id.chronometer);
        btStart =findViewById(R.id.bt_start);
        btStop =findViewById(R.id.bt_stop);

        handler= new Handler();
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){tStart = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                chronometer.start();
                isResume = true;
                btStop.setVisibility(View.GONE);
                btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
                else{
                 tBuff += tMilliSec;
                 handler.removeCallbacks(runnable);
                 chronometer.stop();
                 isResume=false;
                 btStop.setVisibility(View.VISIBLE);
                 btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                }
            }
        });



        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24));
                    tMilliSec = 0L;
                    tStart = 0L;
                    tBuff =0L;
                    tUpdate = 0L;
                    sec =0;
                    min = 0;
                    millisec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        openActivity1();
        return super.onOptionsItemSelected(item);
    }

    public void openActivity1() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec= SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec =(int) (tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            millisec = (int) (tUpdate%100);
            chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",millisec));
            handler.postDelayed(this,60);
        }
    };

}