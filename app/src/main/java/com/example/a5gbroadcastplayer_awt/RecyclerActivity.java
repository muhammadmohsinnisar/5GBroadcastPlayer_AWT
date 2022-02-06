package com.example.a5gbroadcastplayer_awt;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.a5gbroadcastplayer_awt.Adapter.MyAdapter;
//import com.example.a5gbroadcastplayer_awt.Interface.ILoadMore;
//import com.example.a5gbroadcastplayer_awt.Model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecyclerActivity extends AppCompatActivity {

   // List<Item> items = new ArrayList<>();
    //MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_recycler);

        //RadomData
        random10Data();

        //init View
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new MyAdapter(recyclerView,this,items);
        //recyclerView.setAdapter(adapter);

        //set load more event
        adapter.setiLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(items.size() <= 20){
                    items.add(null);
                    adapter.notifyItemInserted(items.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                            adapter.notifyItemRemoved(items.size());

                            //Random more data
                            int index = items.size();
                            int end = index+10;
                            for(int i = index; i<end; i++){
                                String name = UUID.randomUUID().toString();
                                Item item  = new Item(name, name.length());
                                items.add(item);
                            }
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },5000);
                }else {
                    Toast.makeText(RecyclerActivity.this,"Load data completed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void random10Data() {
        for (int i = 0; i<10;i++){
            String name = UUID.randomUUID().toString();
            Item item = new Item(name, name.length());

        }
    }*/
}
}
