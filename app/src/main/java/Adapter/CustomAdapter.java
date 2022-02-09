package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a5gbroadcastplayer_awt.ChannelActivity;
import com.example.a5gbroadcastplayer_awt.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<ChannelActivity.CustomObject> localDataSet;
    private Context context;
    static String[] nameChannel;
    static String[] urlChannel;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ShapeableImageView imageView;
        TextView channelNameView;
        TextView testTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            channelNameView =  (TextView) view.findViewById(R.id.channel_name);
            testTextView = (TextView) view.findViewById(R.id.testTextView);
            imageView = (ShapeableImageView) view.findViewById(R.id.image_channel);
        }

        public ShapeableImageView getImageView() {
            return imageView;
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getLayoutPosition();
            String name = nameChannel[itemPosition];
            String url = urlChannel[itemPosition];
            ChannelActivity.CustomObject object = new ChannelActivity.CustomObject(name,url);

        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(List<ChannelActivity.CustomObject> dataSet, Context c, String[] cN, String[] cU) {
        localDataSet = dataSet;
        context = c;
        nameChannel = cN;
        urlChannel = cU;
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

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.channelNameView.setText(nameChannel[position]);
        viewHolder.testTextView.setText(urlChannel[position]);
        viewHolder.imageView.setBackground(context.getDrawable(R.drawable.ic_launcher_background));
      // Glide.with(context).
            //   load("https://media.istockphoto.com/photos/fresh-citrus-juices-picture-id158268808?k=20&m=158268808&s=612x612&w=0&h=9mUMCBDaY-JYqR7m9r_mi0-Ta0RIebZ3DpxyimSQ7Fc=").into(viewHolder.getImageView());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
