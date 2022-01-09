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

    String url1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        ExoPlayer simpleExoPlayer = new ExoPlayer.Builder(this).build();
        PlayerView playerView = findViewById(R.id.exo_player_view);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url1);
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
    }
}