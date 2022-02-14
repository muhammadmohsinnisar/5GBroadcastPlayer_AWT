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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Adapter.CustomAdapter;
import Model.CustomModel;

public class ChannelActivity extends AppCompatActivity implements CustomAdapter.onItemClicked {

    MaterialButton savedVideo;
    MaterialButton casting;
    MaterialButton search;
    MaterialButton viewLayout;
    FloatingActionButton createChannel;
    RecyclerView recycler;
    private Context context;
    public List<CustomModel> dataSet;
    CustomAdapter customAdapter;
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
        //channelText2 = findViewById(R.id.testTextView);
        channelName = getResources().getStringArray(R.array.channel_name);
        channelURL = getResources().getStringArray(R.array.channel_URL);
        dataSet = new ArrayList<>();

        //CustomModel customModel = new CustomModel();
        //List<CustomModel> dataSet = new ArrayList<>();
        //customAdapter = new CustomAdapter(dataSet, context);
        //doInBackground(dataSet);
        customAdapter = loadChannelList();
        customAdapter.setOnClick(this::onItemClick);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,RecyclerView.VERTICAL,false);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(customAdapter);
        casting = findViewById(R.id.bt_cast);
        search = findViewById(R.id.bt_search);
        viewLayout = findViewById(R.id.bt_view);
        savedVideo = findViewById(R.id.bt_savedvideo);
        createChannel = findViewById(R.id.bt_channeladd);

        savedVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedVideo = new Intent(getApplicationContext(), RecyclerTestActivity.class);
                startActivity(savedVideo);
            }
        });

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"View change button", Toast.LENGTH_LONG).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Search button", Toast.LENGTH_LONG).show();
            }
        });

        casting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Casting button", Toast.LENGTH_LONG).show();
            }
        });

        createChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"create channel button", Toast.LENGTH_LONG).show();
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
        Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show();
        Intent in = new Intent(this, PlayerActivity.class);
        in.putExtra("name", customAdapter.getLocalDataSet().get(position).getChannelName());
        in.putExtra("url", customAdapter.getLocalDataSet().get(position).getChannelUrl());
        startActivity(in);
    }

    public CustomAdapter loadChannelList() {

        CustomAdapter adapter = new CustomAdapter(dataSet, context);;

        try {
            //Toast.makeText(context,"load channels",Toast.LENGTH_SHORT).show();
            URL url = new URL("http://ffmpeg.hilbig17.de/channellist.xml");
            Log.d("Nodes","Loading channels from url: " + url.toString());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("channel");

            List<CustomModel> dataSet2 = new ArrayList<>();
            Log.d("Nodes","Laenge: " + nodeList.getLength());

            for (int i = 0; i < nodeList.getLength(); i++) {
                CustomObject object = new CustomObject();

                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("name");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                Log.d("Nodes","Name = " + ((Node) nameList.item(0)).getNodeValue());
                object.setChannelName(((Node) nameList.item(0)).getNodeValue());

                nameList = fstElmnt.getElementsByTagName("url");
                nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                Log.d("Nodes","Url = " + ((Node) nameList.item(0)).getNodeValue());
                object.setChannelUrl(((Node) nameList.item(0)).getNodeValue());

                nameList = fstElmnt.getElementsByTagName("image");
                nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                Log.d("Nodes","Image = " + ((Node) nameList.item(0)).getNodeValue());
                object.setChannelImage(((Node) nameList.item(0)).getNodeValue());

                dataSet2.add(object);
            }

            adapter = new CustomAdapter(dataSet2, context);

        } catch (Exception e) {
            System.out.println("XML Parsing Excpetion = " + e);
        }

        return adapter;

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


