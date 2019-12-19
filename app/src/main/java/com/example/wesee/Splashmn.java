package com.example.wesee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

public class Splashmn extends AppCompatActivity {
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    {
                        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                .getBoolean("isFirstRun", true);

                        if (isFirstRun) {
                            //show sign up activity
                            startActivity(new Intent(Splashmn.this, Selectopt.class));

                        }
                        else
                        {
                            startActivity(new Intent(Splashmn.this, Blindreg.class));
                        }


                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                .putBoolean("isFirstRun", false).commit();

                    }
                }
            }
        };
        thread.start();


    }}
