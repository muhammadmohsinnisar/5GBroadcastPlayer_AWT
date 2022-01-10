package com.example.a5gbroadcastplayer_awt;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

public class PlayerActivity extends AppCompatActivity{


    private static final String STREAM_URL = "http://ftp.itec.aau.at/datasets/DASHDataset2014/TearsOfSteel/2sec/TearsOfSteel_2s_onDemand_2014_05_09.mpd";
    ExoPlayer player;
    PlayerView playerView;
    // creating a variable for exoplayer
    // Need to add a binder to the view
    //The context might be cause of failure

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerView =  new PlayerView(getApplicationContext());
        playerView.findViewById(R.id.playerView);
        playerView.setPlayer(player);
        initializePlayer();
    }

    private void initializePlayer(){
        DefaultDataSource.Factory mediaDataSourceFactory = new DefaultDataSource.Factory(getApplicationContext());
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(STREAM_URL));
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(mediaDataSourceFactory);

        // creating a variable for exoplayer with MediaSource
        player = new ExoPlayer.Builder(getApplicationContext())
                .setMediaSourceFactory(mediaSourceFactory)
                .build();

        player.addMediaSource(mediaSource);
        player.play();
    }
}
