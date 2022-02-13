package com.example.a5gbroadcastplayer_awt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.CustomAdapter;
import Model.CustomModel;

public class ChannelActivity extends AppCompatActivity implements CustomAdapter.onItemClicked {

    MaterialButton savedVideo;
    RecyclerView recycler;
    private Context context;
    public List<CustomModel> dataSet;
    TextView channelText;
    TextView channelText2;
    ShapeableImageView box;
    String[] channelName, channelURL;
    private static final String TAG = "ChannelActivity.java";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_recycler);
        recycler = findViewById(R.id.recycler);
        context = this;
        box = findViewById(R.id.image_logo);
        channelText = findViewById(R.id.channel_name);
        channelText2 = findViewById(R.id.testTextView);
        channelName = getResources().getStringArray(R.array.channel_name);
        channelURL = getResources().getStringArray(R.array.channel_URL);

        CustomModel customModel = new CustomModel();
        List<CustomModel> dataSet = new ArrayList<>();
        CustomAdapter customAdapter = new CustomAdapter(dataSet, context);
        doInBackground(dataSet);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,RecyclerView.VERTICAL,false);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(customAdapter);
        customAdapter.setOnClick(this::onItemClick);

        savedVideo = findViewById(R.id.bt_savedvideo);
        savedVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedVideo = new Intent(getApplicationContext(), RecyclerTestActivity.class);
                startActivity(savedVideo);
            }
        });
        //TODO replace ImageView with View and check with the link

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ChannelActivity.this.finish();
    }

    @Override
    public void onItemClick(int position) {
        CustomObject object = new CustomObject();
        object.setChannelName(getChannelName(position));
        object.setChannelUrl(getChannelUrl(position));
        Intent in = new Intent(this, PlayerActivity.class);
        String channelName = object.getChannelName();
        String channelURL = object.getChannelUrl();
        in.putExtra("name", channelName);
        in.putExtra("url", channelURL);
        startActivity(in);
        Toast.makeText(this,"This click works!!!onItemClick!!",Toast.LENGTH_LONG).show();
    }

    public void doInBackground(List<CustomModel> list){
        String[] channelName = getResources().getStringArray(R.array.channel_name);
        String[] channelURL = getResources().getStringArray(R.array.channel_URL);
        int j = channelURL.length;

        //CustomObject object = new CustomObject();
        if(channelName != null){
            for (int i = 0; i < j; i++) {
                String name = getChannelName(i);
                String url = getChannelUrl(i);
                CustomObject object = new CustomObject(name,url);
                object.setChannelName(name);
                object.setChannelUrl(url);
                list.add(object);
            }

           // Log.d("List generated",""+dataSet.size());

        } else {
            Log.d("List not generated",""+dataSet.size());
            Toast.makeText(context,"doInbackgroud failed!!!",Toast.LENGTH_LONG).show();
        }
    }

    public void openActivity2(int i) {
        //channelURL = getResources().getStringArray(R.array.channel_URL);
        String cU = channelURL[i];
        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra("url", cU);
        startActivity(intent);
        finish();
    }

    public String getName(String[] channelName){
        String name = null;
        for(int i = 0 ; i<size();i++) {
            name = channelName[i];
        }
        return name;
    }

    public String getUrl(String[] channelURL){
        String url = null;
        for(int i = 0 ; i<size();i++) {
            url = channelURL[i];
        }
        return url;
    }

    public void onClick(View view) {
        int itemPosition = recycler.getChildLayoutPosition(view);
        Intent intent = new Intent();
        intent.putExtra("name",getChannelName(itemPosition));
        intent.putExtra("url",getChannelUrl(itemPosition));
        startActivity(intent);
    }


    public static class CustomObject extends CustomModel {
        String channelName;
        String channelUrl;
        int itemPos;

        CustomObject(String[] chN, String[] chU,int item){
            itemPos = item;
            channelName = chN[itemPos];
            channelUrl = chU[itemPos];
        }

        public CustomObject(String cN, String cU) {
            channelName = cN;
            channelUrl = cU;
        }

        public CustomObject() {
        }
    }

    public int getPosition(RecyclerView recyclerView, View view){
        int pos = recyclerView.getLayoutManager().getPosition(view);
        return pos;
    }
    public String getChannelName(int itemPosition) {
        channelName = getResources().getStringArray(R.array.channel_name);
        String cN = channelName[itemPosition];
        return cN;
    }

    public String getChannelUrl(int itemPosition) {
        channelURL = getResources().getStringArray(R.array.channel_URL);
        String cU = channelURL[itemPosition];
        return cU;
    }

    public int size(){
        int length = getResources().getStringArray(R.array.channel_URL).length;
        for(int i = 0; i <= length; i++) {
        length = i;
        }
        return length;
    }


    //TODO This function will take the number of items in resource and create objects accordingly.
    public void objectCreator (){
        for (int i = 0; i < size(); i++) {
            List<CustomModel> dataSet = new ArrayList<>();
            CustomModel object = new CustomModel(channelName[i],channelURL[i]);
            dataSet.add(object);

        }
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

    }

}


