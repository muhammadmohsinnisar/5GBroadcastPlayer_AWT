package com.example.a5gbroadcastplayer_awt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;
    Animation topAnim,bottomAnim;
    ImageView logo;
    TextView nameApp, starter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks
        logo = findViewById(R.id.im_logo);
        nameApp = findViewById(R.id.txt_name);
        starter = findViewById(R.id.txt_product);

        logo.startAnimation(topAnim);
        nameApp.startAnimation(topAnim);
        starter.startAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                openActivity2();
            }
        },SPLASH_SCREEN);

    }

    private void openActivity2() {
        Intent intent = new Intent(this, ChannelActivity.class);
        startActivity(intent);
        finish();
    }

}
