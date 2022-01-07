package com.example.a5gbroadcastplayer_awt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button toPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toPlayer = findViewById(R.id.bt_play);

        toPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent player = new Intent(getApplicationContext(), PlayerActivity.class);
                startActivity(player);

            }
        });
    }

}
