package com.example.a5gbroadcastplayer_awt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PlayerActivity.java";
    ExoPlayer simpleExoPlayer;
    PlayerView playerView;
    String channelName, channelUrl;


    //TODO Height/Width control does not work
    //TODO Find a way to use exo_control_view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_frame_player);
        channelName = getIntent().getExtras().getString("name");
        channelUrl = getIntent().getExtras().getString("url");
        playerView =  new PlayerView(getApplicationContext());
        playerView.findViewById(R.id.player);
        playerView.setPlayer(simpleExoPlayer);
        initializePlayer(channelUrl);
    }

    @Override
    public void onBackPressed() {
        Thread.currentThread().interrupt();
        super.onBackPressed();
        Intent intent2 = new Intent(getApplicationContext(),ChannelActivity.class);
        stopPlayer();
        this.finish();
    }

    private void initializePlayer(String url){
        DefaultDataSource.Factory mediaDataSourceFactory = new DefaultDataSource.Factory(getApplicationContext());
        //MediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(STREAM_URL));
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(mediaDataSourceFactory);
        simpleExoPlayer = new ExoPlayer.Builder(this).build();
        PlayerView playerView = findViewById(R.id.player);
        PlayerControlView playerControlView = new PlayerControlView(this);
        //playerControlView.findViewById(R.id.exo_player_control_view);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url);

        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void stopPlayer(){
        simpleExoPlayer.stop();
        playerView.onPause();
        playerView.removeView(playerView);
        simpleExoPlayer.release();
        simpleExoPlayer.clearMediaItems();

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
        simpleExoPlayer.release();
    }

    @Override
    public void onClick(View view) {
    Toast.makeText(getApplicationContext(),"The OnClick works!!", Toast.LENGTH_LONG).show();
    }
}
