package com.example.letsplay;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.content.ContentResolver;
import android.preference.PreferenceManager;
import android.net.Uri;
import android.provider.*;
import android.util.*;
import android.database.*;
import android.app.*;
import android.content.Context;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Music extends Fragment {

    ListView mListView;
    ArrayList<File> songArrayList;
    ArrayAdapter<String> mArrayAdapter;
    String[] songs;
    int albumId;
    private NotificationManager mNotificationManager;


    public Music() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // to display menu in action bar
        //setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_music, container, false);



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_audiotrack_black_24dp) // notification icon
                .setContentTitle("Simple notification") // title
                .setContentText("Hello word") // body message
                .setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());


        mListView = view.findViewById(R.id.musicListView);

        askStoragePermissions();

        return view;
    }

    public ArrayList<File> findMusics(File file){

        ArrayList<File> musicLists = new ArrayList<File>();

        File[] files = file.listFiles();

        for(File singleFile: files ){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                musicLists.addAll(findMusics(singleFile));
                Collections.sort(musicLists, Collections.reverseOrder());
            }else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".m4a") || singleFile.getName().endsWith(".wav") || singleFile.getName().endsWith(".m4b")){
                    musicLists.add(singleFile);
                    Collections.sort(musicLists, Collections.reverseOrder());
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

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_audiotrack_black_24dp) // notification icon
                        .setContentTitle("Simple notification") // title
                        .setContentText("Hello word") // body message
                        .setAutoCancel(true);
                NotificationManager mNotificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());

                //Send the notification.
                //mNotificationManager.notify(1, notification);

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

    public void askStoragePermissions(){
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


