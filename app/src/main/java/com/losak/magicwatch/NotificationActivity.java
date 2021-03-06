package com.losak.magicwatch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends Activity {

    private TextView mTextView;
    static String RECOGNIZED_TEXT = "No text recognized.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mTextView = (TextView) findViewById(R.id.text);
        mTextView.setText(RECOGNIZED_TEXT);
    }
}
