package com.example.a5gbroadcastplayer_awt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.button.MaterialButton;

public class ChannelActivity extends AppCompatActivity {
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    CardView cardView4;
    CardView cardView5;
    CardView cardView6;
    ImageView player1;
    ImageView player2;
    ImageView player3;
    ImageView player4;
    ImageView player5;
    ImageView player6;
    MaterialButton savedVideo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channellist);
        cardView1 = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);
        cardView5 = findViewById(R.id.cardView5);
        cardView6 = findViewById(R.id.cardView6);
        player1 = findViewById(R.id.image_view1);
        player2 = findViewById(R.id.image_view2);
        player3 = findViewById(R.id.image_view3);
        player4 = findViewById(R.id.image_view4);
        player5 = findViewById(R.id.image_view5);
        player6 = findViewById(R.id.image_view6);
        //savedVideo = findViewById(R.id.bt_saved);
        String brChannel = "https://bildlivehls-lh.akamaihd.net/i/bildtv247dach_1@107603/master.m3u8";
        String arteChannel = "https://bildlivehls-lh.akamaihd.net/i/bildtv247dach_1@107603/master.m3u8";
        String dashChannel = "http://livesim.dashif.org/livesim/segtimeline_1/testpic_2s/Manifest.mpd";
        String biggerFun = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
        String elephantDream = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
        //openActivity2(brChannel);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.isMemoryCacheable();
        Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load("http://goo.gl/gEgYUd").into(player1);
        Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load("http://goo.gl/gEgYUd").into(player2);
        Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load("http://goo.gl/gEgYUd").into(player3);
        Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load("http://goo.gl/gEgYUd").into(player4);
        Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load("http://goo.gl/gEgYUd").into(player5);
        Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load("http://goo.gl/gEgYUd").into(player6);
        //TODO replace ImageView with View and check with the link

       cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openActivity2(brChannel);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openActivity2(arteChannel);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openActivity2(dashChannel);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2(biggerFun);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2(elephantDream);
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2("https://bildlivehls-lh.akamaihd.net/i/bildtv247dach_1@107603/master.m3u8");
            }
        });

        /*savedVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saved = new Intent(getApplicationContext(), RecyclerTestActivity.class);
                startActivity(saved);
            }
        });*/

    }


    private void openActivity2(String channel) {

        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra("source", channel);
        startActivity(intent);
        finish();
    }
}
