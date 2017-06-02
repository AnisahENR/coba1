package com.example.anisahdenis.coba1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ScreenSplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(ScreenSplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
