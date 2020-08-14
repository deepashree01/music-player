package com.example.letsplay;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CurrentList extends AppCompatActivity {
    Toolbar mListToolbar;
    ArrayList<File> currentSongs;
    ArrayList<File> filteredSongs;
    ListView mListView,searchlist;
    String songs[];
    String[] addSong;
    //File fs;
    ArrayAdapter<String> mArrayAdapter;
    ArrayAdapter<String> filterAdapter;
    FloatingActionButton fab;
    String value="";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_list);
        mListToolbar = findViewById(R.id.mListToolbar);
        setSupportActionBar(mListToolbar);
        getSupportActionBar().setTitle("Now Playing");
        mListView = findViewById(R.id.currentList);
        searchlist = findViewById(R.id.currentList);





        Intent songData = getIntent();
        currentSongs = (ArrayList) songData.getParcelableArrayListExtra("songsList");
       // filteredSongs =(ArrayList) songData.getParcelableArrayListExtra("songsList");
        songs = new String[currentSongs.size()];
       // addSong = new String[filteredSongs.size()];

        for(int i = 0; i < currentSongs.size();i++){
            songs[i] = currentSongs.get(i).getName().replace(".mp3","").replace(".m4a","").replace(".wav","").replace(".m4b","");
            Log.i("Song Name: ",currentSongs.get(i).getName());
        }





      mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,songs);
        mListView.setAdapter(mArrayAdapter);




        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = mListView.getItemAtPosition(position).toString();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CurrentList.this);
                prefs.edit().putString("sname", songName).commit();

               Intent play = new Intent(CurrentList.this,Player.class);
                play.putExtra("songs",currentSongs).putExtra("songName",songName).putExtra("position",position);

                startActivity(play);
                Toast.makeText(CurrentList.this,songName,Toast.LENGTH_LONG).show();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentSongs.remove(i);
                mArrayAdapter.notifyDataSetChanged();
                Intent play = new Intent(CurrentList.this,Player.class);
                play.putExtra("songs",currentSongs);
                startActivity(play);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulist,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               mArrayAdapter.getFilter().filter(s);

               //searchlist.setAdapter(mArrayAdapter);


                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void setAddSong(File addSong) {
        currentSongs.add(addSong);
    }
}
