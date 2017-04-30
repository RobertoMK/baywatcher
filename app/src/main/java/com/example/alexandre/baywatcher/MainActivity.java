package com.example.alexandre.baywatcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView txt1 = (TextView) findViewById(R.id.textView);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "bb.ttf");
        txt1.setTypeface(font1);


        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {

                } finally {
                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
                    finish();
                }
            }
        };
        splashTread.start();
    }
}
