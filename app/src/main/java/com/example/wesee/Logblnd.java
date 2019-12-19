package com.example.wesee;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class Logblnd extends AppCompatActivity {
  TextView txt1;
  EditText edtCntrlId;
    private TextToSpeech tts;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logblnd);
        txt1=(TextView)findViewById(R.id.frgtid);
        edtCntrlId=(EditText)findViewById(R.id.edt_controlid);
        handler = new Handler();
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                } else {
                    Log.e("TTS", "Initialisation Failed!");
                }
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listen();
            }
        }, 2000);
    }

    private void listen(){

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(Logblnd.this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String inSpeech = res.get(0);
                recognition(inSpeech);
            }
        }
    }
    private void recognition(String text) {
        edtCntrlId.setText(text);
        Toast.makeText(this,edtCntrlId.getText().toString(),Toast.LENGTH_SHORT).show();
        if(edtCntrlId.getText().toString().equals("123")){
            speak("Login Successful");
        }
        else{
            speak("Enter Valid Control Id  . If You Forgot Control Id Enter Yes");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listen();
                }
            }, 4000);
            if(edtCntrlId.getText().toString().equals("yes")) {
                speak("Enter your phone number you will be sent control id");
                Intent it = new Intent(Logblnd.this, Forgotpwd.class);
                startActivity(it);
            }
        }

    }
}