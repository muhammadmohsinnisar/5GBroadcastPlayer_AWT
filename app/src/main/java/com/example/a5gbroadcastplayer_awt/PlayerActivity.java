package com.example.a5gbroadcastplayer_awt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "PlayerActivity.java";
    ExoPlayer simpleExoPlayer;
    PlayerView playerView;
    String channelName, channelUrl;


    //TODO Height/Width control does not work
    //TODO Find a way to use exo_control_view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_frame_player);
        channelName = getIntent().getExtras().getString("name");
        channelUrl = getIntent().getExtras().getString("url");
        playerView = new PlayerView(getApplicationContext());
        playerView.findViewById(R.id.player);
        playerView.setPlayer(simpleExoPlayer);
        initializePlayer(channelUrl);
    }

    @Override
    public void onBackPressed() {
        Thread.currentThread().interrupt();
        super.onBackPressed();
        Intent intent2 = new Intent(getApplicationContext(),ChannelActivity.class);
        stopPlayer();
        this.finish();
    }

    private void initializePlayer(String url){
        DefaultDataSource.Factory mediaDataSourceFactory = new DefaultDataSource.Factory(getApplicationContext());
        //MediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(STREAM_URL));
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(mediaDataSourceFactory);
        simpleExoPlayer = new ExoPlayer.Builder(this).build();
        PlayerView playerView = findViewById(R.id.player);
        PlayerControlView playerControlView = new PlayerControlView(this);
        //playerControlView.findViewById(R.id.exo_player_control_view);
        playerView.setPlayer(simpleExoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url);

        loadManifest();
        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.setPlayWhenReady(true);
    }

    public static String docToString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File dir = new File(mcoContext.getFilesDir(), "mydir");
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadManifest() {

        try {
            //Toast.makeText(context,"load channels",Toast.LENGTH_SHORT).show();
            URL url = new URL(channelUrl);
            Log.d("manifest","Loading manifest from url: " + url.toString());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            Log.d("manifest", docToString(doc));

            writeFileOnInternalStorage(this, "test.xml", docToString(doc));

            NodeList nodeList = doc.getElementsByTagName("channel");

            /*for (int i = 0; i < nodeList.getLength(); i++) {
                ChannelActivity.CustomObject object = new ChannelActivity.CustomObject();

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

            adapter = new CustomAdapter(dataSet2, context);*/

        } catch (Exception e) {
            System.out.println("XML Parsing Excpetion = " + e);
        }


    }

    private void stopPlayer(){
        simpleExoPlayer.stop();
        //playerView.onPause();
        //playerView.removeView(playerView);
        simpleExoPlayer.release();
        simpleExoPlayer.clearMediaItems();

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
        simpleExoPlayer.release();
    }

    @Override
    public void onClick(View view) {
    Toast.makeText(getApplicationContext(),"The OnClick works!!", Toast.LENGTH_LONG).show();
    }
}
