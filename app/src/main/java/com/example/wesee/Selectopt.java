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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class Selectopt extends AppCompatActivity {
    private TextToSpeech tts;
    View view ;

    ImageButton myimg1,myimg2;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectopt);
view=(View)findViewById(R.id.viewid);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("hin","IND"));

                    tts.setSpeechRate(0.8f);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    tts.setPitch(0.7f);
                    speak("नमस्कार । आपका वी सी में स्वागत है । यदि आपको वी सी  एप में रजिस्ट्रेसन करना है तो आप ब्लाइंड के रूप अथवा वॉलंटियर के रूप में कर सकते है । ब्लाइंड के रूप में रजिस्टर कर ने के लिए ब्लाइंड बोलिये या वॉलंटियर के रूप में रजिस्टर कर ने के लिए वॉलंटियर बोलिये | रजिस्टर करने के लिए आप अपनी स्क्रीन पर एक बार टेप करे ।\n");   //          speak("Hello Welcome to We see . we are here to  guide you with your navigation , with this app you can find objects ,video call volunteer to guide you with your daily activities ,  touch once on your screen to get started");

                } else {
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //speak("ब्लाइंड के रूप में रजिस्टर कर ने के लिए ब्लाइंड बोलिये या वॉलंटियर के रूप में रजिस्टर कर ने के लिए वॉलंटियर बोलिये ।");

              speak("  ");
                listen();

                //  final Handler handler = new Handler();
            /*    handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       listen();
                    }
                }, 8000);*/
            }

        });

        myimg1 = (ImageButton) findViewById(R.id.imgbtnblnd);
myimg1.setEnabled(false);
        myimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent beach = new Intent(Selectopt.this, Blindreg.class);
                startActivity(beach);
            }
        });
        myimg2 = (ImageButton) findViewById(R.id.imgbtnvol);
        myimg2.setEnabled(false);

        myimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent beach = new Intent(Selectopt.this, Volreg.class);
                startActivity(beach);
            }
        });
        btn = findViewById(R.id.imgslctlog);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent beach = new Intent(Selectopt.this, Logblnd.class);
                startActivity(beach);
            }
        });

        }
    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    private void listen(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
        try {
           startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(Selectopt.this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
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
        Log.e("Speech", "" + text);

        String[] speech = text.split(" ");
        if(text.contains("blind"))
        {
            Intent intent = new Intent(Selectopt.this,Blindreg.class);
            startActivity(intent);
            speak("Registering as Blind . To get yourself registered answer few questions . What is your first name");

        }
        else if(text.contains("volunteer"))
        {
            Intent intent = new Intent(Selectopt.this,Volreg.class);
            startActivity(intent);
            speak("Registering as volunteer");
        }

    }
    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}

