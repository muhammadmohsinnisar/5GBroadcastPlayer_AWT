package com.example.a5gbroadcastplayer_awt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class DownloadPlayerActivity extends AppCompatActivity {
    String contentUrl;
    //   Uri contentUri;
    MediaItem mediaItem;
    List<String> mediaItems;
    int position;
    String mediaItemString;
    ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_player);

        position = getIntent().getIntExtra("position", 0);
        mediaItem = CacheMedia.getInstance().getMediaItems().get(position);


        Gson gson = new Gson();
        Type type = new TypeToken<MediaItem>() {
        }.getType();



        SimpleCache downloadCache = VideoCache.getInstance(this);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
        // CacheDataSource.Factory cacheDataSourceFactory=new CacheDataSource.Factory().setUpstreamDataSourceFactory(dataSourceFactory)


        CacheDataSource.Factory cacheDataSourceFactory =
                new CacheDataSource.Factory()
                        .setCache(downloadCache)
                        .setUpstreamDataSourceFactory(dataSourceFactory)
                        .setCacheWriteDataSinkFactory(null); // Disable writing.

        player = new ExoPlayer.Builder(this)
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(cacheDataSourceFactory))
                .build();

        PlayerView playerView = findViewById(R.id.player);
        PlayerControlView playerControlView = new PlayerControlView(this);
        //playerControlView.findViewById(R.id.exo_player_control_view);
        playerView.setPlayer(player);


        ProgressiveMediaSource mediaSource =
                new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                        .createMediaSource(mediaItem);

        player.setMediaSource(mediaSource);

        player.prepare();
        player.play();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
        player.release();
    }
}
