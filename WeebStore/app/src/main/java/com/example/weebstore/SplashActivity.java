package com.example.weebstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity
{
    //Animation topA,bottomA;
    //ImageView img;
    //TextView s_title,title_txt;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // Adding delay timer to jump to mainActivity
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                //Toast.makeText(SplashActivity.this, "Welcome to Prime eShop", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        }, 3000);

//        img = (ImageView) findViewById(R.id.splash_img);

//        topA = AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomA = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
//
//        s_title = (TextView) findViewById(R.id.title);
//        title_txt = (TextView) findViewById(R.id.title_text);
//
//        img.setAnimation(topA);
//        s_title.setAnimation(bottomA);
//        title_txt.setAnimation(bottomA);
//        SystemClock.sleep(4000);

//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(mainIntent);
//                Toast.makeText(SplashActivity.this, "Welcome to Compras BD", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });



    }
}
