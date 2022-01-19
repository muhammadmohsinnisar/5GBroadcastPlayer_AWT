package com.example.a5gbroadcastplayer_awt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

public class PlayerActivity extends AppCompatActivity{

    private static final String STREAM_URL = "http://ftp.itec.aau.at/datasets/DASHDataset2014/TearsOfSteel/2sec/TearsOfSteel_2s_onDemand_2014_05_09.mpd";
    ExoPlayer player;
    PlayerView playerView;
    ImageView fullScreenButton;
    boolean fullScreen = false;
    // creating a variable for exoplayer
    // Need to add a binder to the view
    //The context might be cause of failure
    //String url1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
    //String url1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
    //String url1 = "https://bildlivehls-lh.akamaihd.net/i/bildtv247dach_1@107603/master.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerView =  new PlayerView(getApplicationContext());
        playerView.findViewById(R.id.player);
        fullScreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);


        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
        Bundle extras = getIntent().getExtras();
        String source = extras.getString("source");
        /*fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fullScreen){
                    fullScreenButton.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this,R.drawable.ic_fullscreen_open));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if(getSupportActionBar() !=null){
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    fullScreen = false;

                }else {
                    fullScreenButton.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this,R.drawable.ic_fullscreen_close));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);

                    fullScreen = true;
                }
            }
        });*/
        initializePlayer(source);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent2 = new Intent(getApplicationContext(),ChannelActivity.class);
        stopPlayer();
        player.release();
        onDestroy();
        startActivity(intent2);
    }

    @Override
    public void onDestroy(){
        player.release();
        super.onDestroy();
    }

    private void initializePlayer(String source){
        DefaultDataSource.Factory mediaDataSourceFactory = new DefaultDataSource.Factory(getApplicationContext());
        //MediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(STREAM_URL));
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(mediaDataSourceFactory);

        // creating a variable for exoplayer with MediaSource
        player = new ExoPlayer.Builder(getApplicationContext())
                .setMediaSourceFactory(mediaSourceFactory)
                .build();

        ExoPlayer simpleExoPlayer = new ExoPlayer.Builder(this).build();
        PlayerView playerView = findViewById(R.id.player);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(source);
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.setPlayWhenReady(true);

    }

    private void saveVideo(String source) throws IOException {
        StreamSource recording = null;
        DataInputStream in = new DataInputStream (recording.getInputStream());
        File targetFile = null;
        FileOutputStream videoFile = new FileOutputStream(targetFile);
        int len;
        byte buffer[] = new byte[8192];

        while((len = in.read(buffer)) != -1) {
            videoFile.write(buffer, 0, len);
        }

        videoFile.close();
    }

    private void stopPlayer(){
        player.stop();
        playerView.onPause();

    }
}