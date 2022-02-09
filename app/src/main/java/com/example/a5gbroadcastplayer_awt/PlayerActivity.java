package com.example.a5gbroadcastplayer_awt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

public class PlayerActivity extends AppCompatActivity{

    private static final String TAG = "PlayerActivity.java";
    //private static final String channelUrl = "http://ftp.itec.aau.at/datasets/DASHDataset2014/TearsOfSteel/2sec/TearsOfSteel_2s_onDemand_2014_05_09.mpd";
    ExoPlayer player;
    PlayerView playerView;
    PlayerControlView playerControlView;
    ImageView fullscreenButton;
    boolean fullscreen = false;
    // creating a variable for exoplayer
    // Need to add a binder to the view
    //The context might be cause of failure


    //TODO Height/Width control does not work
    //TODO Find a way to use exo_control_view
    //String url1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
    //String url1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
    //String url1 = "https://bildlivehls-lh.akamaihd.net/i/bildtv247dach_1@107603/master.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_player);
        playerView =  new PlayerView(getApplicationContext());
        playerControlView = new PlayerControlView(getApplicationContext());
        playerView.findViewById(R.id.player);
        playerControlView.findViewById(R.id.exo_control_view);
        playerView.setPlayer(player);
        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_fullscreen_open));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    fullscreen = false;
                }else{
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_fullscreen_close));

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

                    fullscreen = true;
                }
            }
        });
        Bundle extras = getIntent().getExtras();

        //String source = extras.getString("source");
        String channelName = extras.getString("name");
        String channelUrl = extras.getString("url");
        //ChannelActivity channelActivity = new ChannelActivity();
        //String channelUrl = channelActivity.getIntent().getExtras().getString("url");
        //String channelName = channelActivity.getIntent().getExtras().getString("name");
        initializePlayer(channelUrl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent2 = new Intent(getApplicationContext(),ChannelActivity.class);
        stopPlayer();
        PlayerActivity.this.finish();
        startActivity(intent2);
    }

    private void initializePlayer(String url){
        //Bundle extras = getIntent().getExtras();
        DefaultDataSource.Factory mediaDataSourceFactory = new DefaultDataSource.Factory(getApplicationContext());
        //MediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(STREAM_URL));
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(mediaDataSourceFactory);

        // creating a variable for exoplayer with MediaSource
        player = new ExoPlayer.Builder(getApplicationContext())
                .setMediaSourceFactory(mediaSourceFactory)
                .build();

        ExoPlayer simpleExoPlayer = new ExoPlayer.Builder(this).build();
        PlayerView playerView = findViewById(R.id.player);
        PlayerControlView playerControlView = new PlayerControlView(this);
        //playerControlView.findViewById(R.id.exo_player_control_view);
        int h = playerView.getResources().getConfiguration().screenHeightDp;
        int w = playerView.getResources().getConfiguration().screenWidthDp;
        Log.v(TAG, "height : " + h + " weight: " + w);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
        MediaItem mediaItem = MediaItem.fromUri(url);
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();

    }

    private void stopPlayer(){
        player.stop();
        playerView.onPause();
        playerView.removeView(playerView);
        player.release();
    }

    //-------------------------------------------------------ANDROID LIFECYCLE---------------------------------------------------------------------------------------------

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop()...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()...");
        player.release();
    }
}
