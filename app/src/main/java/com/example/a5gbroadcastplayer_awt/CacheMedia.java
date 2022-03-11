package com.example.a5gbroadcastplayer_awt;

import android.app.Application;
import android.net.Uri;

import com.google.android.exoplayer2.MediaItem;

import java.util.List;

public class CacheMedia extends Application {

    private List<MediaItem> mediaItems;
    private List<Uri> uris;
    private static CacheMedia instance;
    public CacheMedia() {
    }



    public CacheMedia(List<Uri> uris) {
        this.uris = uris;
    }


    public CacheMedia(List<MediaItem> mediaItems, List<Uri> uris) {
        this.mediaItems = mediaItems;
        this.uris = uris;
    }

    public static CacheMedia getInstance(){
        if (instance==null){
            instance=new CacheMedia();
        }
        return instance;
    }
    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }
}
