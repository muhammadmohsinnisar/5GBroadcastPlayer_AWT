package com.example.a5gbroadcastplayer_awt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.CustomAdapter;

public class ChannelActivity extends AppCompatActivity {

    MaterialButton savedVideo;
    RecyclerView recycler;
    TextView channnelText;
    String[] channelName, channelURL;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_recycler);
        recycler = findViewById(R.id.recycler);

        channnelText = findViewById(R.id.channel_name);
        savedVideo = findViewById(R.id.bt_savedvideo);

        savedVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedVideo = new Intent(getApplicationContext(), RecyclerTestActivity.class);
                startActivity(savedVideo);
            }
        });
        //savedVideo = findViewById(R.id.bt_saved);
        //openActivity2(brChannel);
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.isMemoryCacheable();
//        Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load("http://goo.gl/gEgYUd").into(player1);


        //TODO replace ImageView with View and check with the link



        List<CustomObject> dataSet = new ArrayList<>();

        CustomObject ob1 = new CustomObject(getChannelName(), getChannelUrl());
        CustomObject ob2 = new CustomObject(getChannelName(), getChannelUrl());
        CustomObject ob3 = new CustomObject(getChannelName(), getChannelUrl());


        //DONE Object creation restricted to resource available.
        //TODO An object to be created against every resource.
        //TODO Map resource to each object, better to use looping to avoid manual creation of object.
        dataSet.add(ob1);
        dataSet.add(ob2);
        dataSet.add(ob3);
        dataSet.add(ob3);
        dataSet.add(ob3);




        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,RecyclerView.VERTICAL,false);
        CustomAdapter customAdapter = new CustomAdapter(dataSet,ChannelActivity.this, channelName, channelURL);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(customAdapter);
        //TODO Set the channelTextView name to Channels name from Resource
        //String[] st_name = getResources().getStringArray(R.array.channel_name);
        //String name = st_name.toString();
        //channnelText.setText(name);
        //TODO Objects are not being added correctly for now. Need to resolve the position issue from resource.
        //TODO Add onItemClickListener for items in recyclerView leading to the Player.
        //TODO Sort out how to get ChannelName and ChannelURL from resource into String for every object.
        //TODO Use Object to create Clickable Items in recyclerview, use these items for accessing player
        //TODO Add SavedVideo Button again.

    }

    private void openActivity2(CustomObject object) {
        channelName = object.channelName;
        channelURL = object.channelUrl;
        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra("name", channelName);
        intent.putExtra("url", channelURL);
        startActivity(intent);
        finish();
    }

    public class CustomObject{
        String[] channelName;
        String[] channelUrl;

        CustomObject(String[] chN, String[] chU){
            channelName = chN;
            channelUrl = chU;
        }
    }
    public String[] getChannelName() {
        channelName = getResources().getStringArray(R.array.channel_name);
        return channelName;
    }

    public String[] getChannelUrl() {
        channelURL = getResources().getStringArray(R.array.channels_URL);
        return channelURL;
    }

}
