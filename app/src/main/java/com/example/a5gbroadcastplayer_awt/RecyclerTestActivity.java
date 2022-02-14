package com.example.a5gbroadcastplayer_awt;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import Adapter.TestAdapter;


public class RecyclerTestActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] s1, s2;
    MaterialButton search;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        search = findViewById(R.id.bt_search);
        recyclerView = findViewById(R.id.recycler);

        s1 = getResources().getStringArray(R.array.place_holders);
        s2 = getResources().getStringArray(R.array.place_holders_length);

        TestAdapter testAdapter = new TestAdapter(this, s1, s2);
        recyclerView.setAdapter(testAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Search button", Toast.LENGTH_LONG).show();
            }
        });
    }
}
