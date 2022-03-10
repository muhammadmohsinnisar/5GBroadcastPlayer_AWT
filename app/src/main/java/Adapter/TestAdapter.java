package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5gbroadcastplayer_awt.ChannelActivity;
import com.example.a5gbroadcastplayer_awt.PlayerActivity;
import com.example.a5gbroadcastplayer_awt.R;
import com.example.a5gbroadcastplayer_awt.RecyclerTestActivity;

import java.util.List;

import Model.CustomModel;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    String data1[], data2[]; //The adapter for ChannelList.

    Context context;
    List<String> contentUris;

    public TestAdapter(Context ct, String[] s1, String[] s2, List<String> contentUris){
        context = ct;
        data1 = s1;
        data2 = s2;
        this.contentUris=contentUris;
    }

    public TestAdapter(RecyclerTestActivity ct, List<String> contentUris) {
        this.context=ct;
        this.contentUris=contentUris;

    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        for (String contentUri : contentUris){
            holder.myText1.setText(data1[position]);
            //holder.myText1.setText();
        }
        holder.myText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomModel object = new CustomModel();
                object.setChannelUrl(contentUris.get(position));
                Intent in = new Intent(context, PlayerActivity.class);
                String channelURL = object.getChannelUrl();
                in.putExtra("url", channelURL);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentUris.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{
        TextView myText1, myText2;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.txtName);
            myText2 = itemView.findViewById(R.id.txtlength);
        }
    }


}