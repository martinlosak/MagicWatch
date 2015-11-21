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

        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(android.R.drawable.ic_btn_speak_now);
        img.setOnClickListener(new View.OnClickListener() {
                                   public void onClick(View v) {
                                       displaySpeechRecognizer();
                                   }
                               }
        );

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()

                                  {
                                      public void onClick(View v) {

                                          new Handler().postDelayed(new Runnable() {
                                              @Override
                                              public void run() {

                                                  Intent i = new Intent();
                                                  i.setAction("com.losak.magicwatch.SHOW_NOTIFICATION");
                                                  i.putExtra(MyPostNotificationReceiver.CONTENT_KEY, getString(R.string.title));
                                                  sendBroadcast(i);
                                              }
                                          }, 1000);
                                          finish();
                                      }
                                  }

        );

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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent();
                    i.setAction("com.losak.magicwatch.SHOW_NOTIFICATION");
                    i.putExtra(MyPostNotificationReceiver.CONTENT_KEY, getString(R.string.title));
                    i.putExtra(MyPostNotificationReceiver.RECOGNIZED_TEXT, spokenText);
                    sendBroadcast(i);
                }
            }, 1000);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
