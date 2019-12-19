package com.example.wesee;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class Blindreg extends AppCompatActivity {
Button btn2;
    private TextToSpeech tts;
    static  int count=0;
     Handler handler;
EditText firstNam,lastName,instName,cityName,phoneNumber,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blindreg);
        handler = new Handler();

        firstNam=findViewById(R.id.fname);
        lastName=findViewById(R.id.lname);
        instName=findViewById(R.id.instname);
        cityName=findViewById(R.id.cname);
        phoneNumber=findViewById(R.id.phno);
        age=findViewById(R.id.age);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listen();
            }
        }, 7000);

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
        btn2 = findViewById(R.id.blndnxt);
        btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent beach = new Intent(Blindreg.this, Controlblnd.class);
                startActivity(beach);
            }
        });

    }

    private void listen(){

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(Blindreg.this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
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
        if(text!=null)
        {
            count++;
        }
        Log.e("Speech", "" + text);

if (count==1)
{
    firstNam.setText(text);
}
else if(count==2)
{
    lastName.setText(text);
}
else if(count==3)
{
    phoneNumber.setText(text);
    String MobilePattern = "[0-9]{10}";

    if(phoneNumber.getText().toString().matches(MobilePattern)) {



    } else if(!phoneNumber.getText().toString().matches(MobilePattern)) {

      speak("Enter Valid number");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listen();
            }
        }, 3000);
    }
}
else if(count==4)
{
    age.setText(text);
}
else if(count==5)
{
    cityName.setText(text);
}
else if(count==6)
{
    instName.setText(text);
}
        if (!(TextUtils.isEmpty(firstNam.getText().toString())))
        {
            if(!(TextUtils.isEmpty(lastName.getText().toString())))
            {
                if(!(TextUtils.isEmpty(phoneNumber.getText().toString())))
                {
                    if (!(TextUtils.isEmpty(age.getText().toString())))
                    {
                      if(!(TextUtils.isEmpty(cityName.getText().toString())))
                       {
                        if(!(TextUtils.isEmpty(instName.getText().toString())))
                        {
                            speak("Enter Your Control id ");
                            Intent it=new Intent(Blindreg.this,Logblnd.class);
                            startActivity(it);


                        }
                        else
                        {
                            speak("Enter your Institute");

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listen();
                                }
                            }, 2000);
                        }

                    }
                      else {
                          speak("Enter your city");

                          handler.postDelayed(new Runnable() {
                              @Override
                              public void run() {
                                  listen();
                              }
                          }, 1500);

                      }
                      }

                    else
                    {
                        speak("Enter your age");

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listen();
                            }
                        }, 2000);

                    }
                }

                else
                {
                    speak("Enter your Phone number");

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listen();
                        }
                    }, 2000);

                }
            }
else
            {
                speak("Enter your last name");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listen();
                    }
                }, 2000);
            }

        }
        else {
            speak("Enter your first name");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listen();
                }
            }, 2000);
        }


    }


}
