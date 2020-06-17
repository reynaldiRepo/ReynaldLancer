package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class splashScreen extends AppCompatActivity {

    private RelativeLayout logoplace;
    private  int time = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logoplace = findViewById(R.id.logo_layout);
        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.fadeupdown);
        logoplace.startAnimation((splashAnim));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, time);

    }
}
