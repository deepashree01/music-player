package com.example.letsplay;



import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.*;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.*;



import com.google.android.youtube.player.*;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;



/**
 * A simple {@link Fragment} subclass.
 */
public class Album extends Fragment {

    ListView mListView;
    ArrayList<File> songArrayList;
    ArrayAdapter<String> mArrayAdapter;
    String[] songs;
    int albumId;
    View view;


    public Album() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_album, container, false);


        mListView = view.findViewById(R.id.youtube_player);

        askStoragePermissions();




        return view;
    }


    public ArrayList<File> findMusics(File file){

        ArrayList<File> musicLists = new ArrayList<File>();

        File[] files = file.listFiles();

        for(File singleFile: files ){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                musicLists.addAll(findMusics(singleFile));

            }else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".m4a") || singleFile.getName().endsWith(".wav") || singleFile.getName().endsWith(".m4b")){
                    musicLists.add(singleFile);

                }
            }
        }

        return musicLists;

    }

    public void display(){

        final ArrayList<File> allSongs = findMusics(Environment.getExternalStorageDirectory());
        songs = new String[allSongs.size()];

        for(int i=0;i<allSongs.size();i++){
            songs[i] = allSongs.get(i).getName().replace(".mp3","").replace(".m4a","").replace(".wav","").replace(".m4b","");
        }


        mArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,songs);
        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // start music player when song name is clicked

                String songName = mListView.getItemAtPosition(position).toString();
                //Intent lyr = new Intent(getActivity(),Playlist.class);
                //lyr.putExtra("sname",songName);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                prefs.edit().putString("sname", songName).commit();

                Intent play = new Intent(getActivity(),Player.class);
                play.putExtra("songs",allSongs).putExtra("songName",songName).putExtra("position",position);
                //startActivity(lyr);
                startActivity(play);
            }
        });

    }

    public void askStoragePermissions() {
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                display();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }
}
