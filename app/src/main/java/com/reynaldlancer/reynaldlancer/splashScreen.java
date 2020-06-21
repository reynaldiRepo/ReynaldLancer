package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class splashScreen extends AppCompatActivity {

    private RelativeLayout logoplace;
    private int time = 5000;
    SQLiteDatabase db;
    SqlHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logoplace = findViewById(R.id.logo_layout);
        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.fadeupdown);

        //read loged user
        helper = new SqlHelper(splashScreen.this);
        logoplace.startAnimation((splashAnim));
        db = helper.getReadableDatabase();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = db.rawQuery("select * from login_data", null);
                int userActive = cursor.getCount();
                if (userActive == 0) {
                    Intent i = new Intent(splashScreen.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(splashScreen.this, HomeActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, time);
    }
}
