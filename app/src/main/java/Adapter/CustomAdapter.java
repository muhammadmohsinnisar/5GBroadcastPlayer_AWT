package Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a5gbroadcastplayer_awt.ChannelActivity;
import com.example.a5gbroadcastplayer_awt.DownloadTracker;
import com.example.a5gbroadcastplayer_awt.MyDownloadService;
import com.example.a5gbroadcastplayer_awt.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.DownloadNotificationHelper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.material.imageview.ShapeableImageView;



import java.io.File;
import java.util.List;
import java.util.concurrent.Executor;

import Model.CustomModel;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<CustomModel> localDataSet;
    private Context context;
    private onItemClicked onClick;
    private onDownloadClick onDownload;
    private static DataSource.Factory dataSourceFactory;
    private static HttpDataSource.Factory httpDataSourceFactory;
    private static DatabaseProvider databaseProvider;
    private static File downloadDirectory;
    private static Cache downloadCache;
    private static DownloadManager downloadManager;
    private static DownloadTracker downloadTracker;
    private static DownloadNotificationHelper downloadNotificationHelper;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public List<CustomModel> getLocalDataSet(){
        return this.localDataSet;
    }

    // interface for managing channel click
    public interface onItemClicked {
        void onItemClick(int position);


    }

    public interface onDownloadClick {

        void onDownloadClick(int pos);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView imageView;
        TextView channelNameView;
        TextView testTextView;
        ImageButton download;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            channelNameView =  (TextView) view.findViewById(R.id.channel_name);
            imageView = (ShapeableImageView) view.findViewById(R.id.image_channel);
            download = (ImageButton) view.findViewById(R.id.download_button);
        }

        public ShapeableImageView getImageView() {
            return imageView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(List<CustomModel> localDataSet, Context context) {
        this.localDataSet = localDataSet;
        this.context = context;

    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_channel, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //get position
        CustomModel customModel = localDataSet.get(position);
        final int pos = position;
        String nameChannel = customModel.getChannelName();
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.channelNameView.setText(customModel.getChannelName());
        //viewHolder.testTextView.setText(customModel.getChannelUrl());
        viewHolder.imageView.setBackground(context.getDrawable(R.drawable.ic_launcher_background2));
        Glide.with(context).
               load(customModel.getChannelImage()).into(viewHolder.getImageView());

        viewHolder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Log.d("pos", "onClick: "+pos);

                onClick.onItemClick(pos);
            }
        });

        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onDownload.onDownloadClick(pos);


            }
        });


    }




    public void setOnClick(onItemClicked onClick){
        this.onClick = onClick;
    }

    public void setOnDownload(onDownloadClick onDownload){
        this.onDownload=onDownload;
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
