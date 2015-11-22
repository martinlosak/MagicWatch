package com.losak.magicwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends Activity {

    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView microphone = (ImageView) findViewById(R.id.imageView);
        microphone.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              displaySpeechRecognizer();
                                          }
                                      }
        );

        //TEST
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send spokenText to new Intent
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent();
                        i.setAction("com.losak.magicwatch.SHOW_NOTIFICATION");
                        i.putExtra(PostNotificationReceiver.CONTENT_KEY, getString(R.string.title));
                        i.putExtra(PostNotificationReceiver.RECOGNIZED_TEXT, "Your pizza will be at your place in 10 minutes. Thanks for your order");
                        sendBroadcast(i);
                    }
                }, 2000);
                finish();
            }
        });
        //TEST

    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            final String spokenText = results.get(0);

            //send spokenText to new Intent
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent();
                    i.setAction("com.losak.magicwatch.SHOW_NOTIFICATION");
                    i.putExtra(PostNotificationReceiver.CONTENT_KEY, getString(R.string.title));
                    i.putExtra(PostNotificationReceiver.RECOGNIZED_TEXT, spokenText);
                    sendBroadcast(i);
                }
            }, 2000);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}