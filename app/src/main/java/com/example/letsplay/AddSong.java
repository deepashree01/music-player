package com.example.letsplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class AddSong extends AppCompatActivity {

    Toolbar mListToolbar;

    ArrayList<File> filteredSongs;
    ListView  searchlist;

    String[] addSong;

    ArrayAdapter<String> filterAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsong);
        searchlist=findViewById(R.id.addList);
        mListToolbar=findViewById(R.id.addListToolbar);
        Intent songData = getIntent();
        filteredSongs = (ArrayList) songData.getParcelableArrayListExtra("songsList");
        addSong = new String[filteredSongs.size()];
        for (int j = 0; j < filteredSongs.size(); j++) {
            addSong[j] = filteredSongs.get(j).getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
            Log.i("song name:", filteredSongs.get(j).getName());
            filterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addSong);
            searchlist.setAdapter(filterAdapter);
        }
        searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File file = (File) searchlist.getItemAtPosition(i);
                filteredSongs.add(file);
            }
        });

    }
}


