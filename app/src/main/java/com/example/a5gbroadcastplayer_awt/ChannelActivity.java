package com.example.a5gbroadcastplayer_awt;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import Adapter.CustomAdapter;

public class ChannelActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton savedVideo;
    RecyclerView recycler;
    TextView channelText;
    TextView channelText2;
    ShapeableImageView box;
    String[] channelName, channelURL;
    //String cN, cU;

    /*public String getCnName() {
        return cnName;
    }

    public void setCnName(String[] cnName) {
        cnName = getChannelName();
        this.cnName = String.valueOf(cnName);
    }

    public String getCnUrl() {
        return cnUrl;
    }

    public void setCnUrl(String[] cnUrl) {
        cnUrl = getChannelUrl(itemPosition);
        this.cnUrl = String.valueOf(cnUrl);
    }

    String cnName, cnUrl;
    int i;
    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_recycler);
        recycler = findViewById(R.id.recycler);
        box = findViewById(R.id.image_logo);
        channelText = findViewById(R.id.channel_name);
        channelText2 = findViewById(R.id.testTextView);
        channelName = getResources().getStringArray(R.array.channel_name);
        channelURL = getResources().getStringArray(R.array.channel_URL);
        //cN = channelText.toString();
        //cU = channelText2.toString();
        //CustomAdapter adapter = new CustomAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,RecyclerView.VERTICAL,false);
        List<CustomObject> dataSet = new ArrayList<>();
        CustomAdapter customAdapter = new CustomAdapter(dataSet,ChannelActivity.this, channelName, channelURL);
        savedVideo = findViewById(R.id.bt_savedvideo);

        savedVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedVideo = new Intent(getApplicationContext(), RecyclerTestActivity.class);
                startActivity(savedVideo);
            }
        });
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(customAdapter);
        recycler.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //int position = recycler.getLayoutManager().getPosition(v);
              Toast.makeText(getApplicationContext(),"click works!!!",Toast.LENGTH_LONG).show();
            }
        });

        //TODO replace ImageView with View and check with the link

        //TODO For now obj are created manually for every channel, want to replace it with function that automatically creates objects
        //Objects representing Items in recyclerview
        CustomObject ob1 = new CustomObject(getChannelName(0), getChannelUrl(0));
        CustomObject ob2 = new CustomObject(getChannelName(1), getChannelUrl(1));
        CustomObject ob3 = new CustomObject(getChannelName(2), getChannelUrl(2));
        CustomObject ob4 = new CustomObject(getChannelName(3),getChannelUrl(3));
        CustomObject ob5 = new CustomObject(getChannelName(4),getChannelUrl(4));

        //DONE Object creation restricted to resource available.
        //TODO An object to be created against every resource.
        //TODO Map resource to each object, better to use looping to avoid manual creation of object.
        dataSet.add(ob1);
        dataSet.add(ob2);
        dataSet.add(ob3);
        dataSet.add(ob4);
        dataSet.add(ob5);

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

    public void openActivity2(int i) {
        channelURL = getResources().getStringArray(R.array.channel_URL);
        String cU = channelURL[i];
        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra("url", cU);
        startActivity(intent);
        finish();
    }

    /*@Override
    public void onClick(View view) {
        int itemPosition = recycler.getChildLayoutPosition(view);
        String name = channelName[itemPosition];
        String url = channelURL[itemPosition];
        openActivity2();

    }*/
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

    @Override
    public void onClick(View view) {
        int itemPosition = recycler.getChildLayoutPosition(view);
        Intent intent = new Intent();
        intent.putExtra("name",getChannelName(itemPosition));
        intent.putExtra("url",getChannelUrl(itemPosition));
        startActivity(intent);
    }


    public static class CustomObject{
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
            List<CustomObject> dataSet = new ArrayList<>();
            CustomObject object = new CustomObject(getChannelName(i),getChannelUrl(i));
            dataSet.add(object);

        }
    }

}
