package com.example.a5gbroadcastplayer_awt;

import static com.example.a5gbroadcastplayer_awt.DemoUtil.DOWNLOAD_NOTIFICATION_CHANNEL_ID;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadCursor;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.PlatformScheduler;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.DownloadNotificationHelper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyDownloadService extends DownloadService {

    private static final int JOB_ID = 1;
    private static final int FOREGROUND_NOTIFICATION_ID = 1;
    private static HttpDataSource.Factory httpDataSourceFactory;

    public MyDownloadService() {
        super(
                FOREGROUND_NOTIFICATION_ID,
                DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
                DOWNLOAD_NOTIFICATION_CHANNEL_ID,
                R.string.exo_download_notification_channel_name,
                /* channelDescriptionResourceId= */ 0);
    }

    @Override
    protected DownloadManager getDownloadManager() {
        // This will only happen once, because getDownloadManager is guaranteed to be called only once
        // in the life cycle of the process.
        DownloadManager downloadManager = DemoUtil.getDownloadManager(/* context= */ this);
        DownloadNotificationHelper downloadNotificationHelper =
                DemoUtil.getDownloadNotificationHelper(/* context= */ this);
        downloadManager.addListener(
                new TerminalStateNotificationHelper(
                        this, downloadNotificationHelper, FOREGROUND_NOTIFICATION_ID + 1));
        return downloadManager;
    }

    @Override
    protected Scheduler getScheduler() {
        return Util.SDK_INT >= 21 ? new PlatformScheduler(this, JOB_ID) : null;
    }

    @Override
    protected Notification getForegroundNotification(
            List<Download> downloads, @Requirements.RequirementFlags int notMetRequirements) {
        return DemoUtil.getDownloadNotificationHelper(/* context= */ this)
                .buildProgressNotification(
                        /* context= */ this,
                        R.drawable.ic_download,
                        /* contentIntent= */ null,
                        /* message= */ null,
                        downloads,
                        notMetRequirements);
    }

    /**
     * Creates and displays notifications for downloads when they complete or fail.
     *
     * <p>This helper will outlive the lifespan of a single instance of {@link MyDownloadService}.
     * It is static to avoid leaking the first {@link MyDownloadService} instance.
     */
    private static final class TerminalStateNotificationHelper implements DownloadManager.Listener {

        private final Context context;
        private final DownloadNotificationHelper notificationHelper;

        private int nextNotificationId;

        public TerminalStateNotificationHelper(
                Context context, DownloadNotificationHelper notificationHelper, int firstNotificationId) {
            this.context = context.getApplicationContext();
            this.notificationHelper = notificationHelper;
            nextNotificationId = firstNotificationId;
        }

        @Override
        public void onDownloadChanged(
                DownloadManager downloadManager, Download download, @Nullable Exception finalException) {
            Notification notification;
            if (download.state == Download.STATE_COMPLETED) {
                notification =
                        notificationHelper.buildDownloadCompletedNotification(
                                context,
                                R.drawable.ic_download_done,
                                /* contentIntent= */ null,
                                Util.fromUtf8Bytes(download.request.data));
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();

                ArrayList<MediaStore.Video.Media> downloadedTracks = new ArrayList<MediaStore.Video.Media>();
                try {
                    DownloadCursor downloadCursor = downloadManager.getDownloadIndex().getDownloads();
                    if (downloadCursor.moveToFirst()) {
                        do {
                            String jsonString =Util.fromUtf8Bytes(downloadCursor.getDownload().request.data);
                            Uri uri = downloadCursor.getDownload().request.uri;

                            Log.d("url", "onDownloadChanged: "+uri);
                            /*
                            //downloadedTracks.add(new MediaStore.Video.Media());
                            DataSource.Factory cacheDataSourceFactory =
                                    new CacheDataSource.Factory()
                                            .setCache(VideoCache.getInstance(context))
                                            .setUpstreamDataSourceFactory(httpDataSourceFactory)
                                            .setCacheWriteDataSinkFactory(null); // Disable writing.

                            ExoPlayer player = new ExoPlayer.Builder(context)
                                    .setMediaSourceFactory(
                                            new DefaultMediaSourceFactory(cacheDataSourceFactory))
                                    .build();

                            ProgressiveMediaSource mediaSource =
                                    new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                                            .createMediaSource(MediaItem.fromUri(uri));
                            player.setMediaSource(mediaSource);
                            player.prepare();

                             */
                        }while (downloadCursor.moveToNext());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }



            } else if (download.state == Download.STATE_FAILED) {
                notification =
                        notificationHelper.buildDownloadFailedNotification(
                                context,
                                R.drawable.ic_download_done,
                                /* contentIntent= */ null,
                                Util.fromUtf8Bytes(download.request.data));
                Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
            } else {
                return;
            }
            NotificationUtil.setNotification(context, nextNotificationId++, notification);
        }
    }
}
