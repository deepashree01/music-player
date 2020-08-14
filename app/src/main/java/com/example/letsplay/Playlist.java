package com.example.letsplay;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Playlist extends Fragment {

    String value1="";
    String value="";
    String lyr="";
    TextView lyText;
    Intent damage;


    //static MediaPlayer mMediaPlayer;
    public Playlist() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        lyText=view.findViewById(R.id.displayly);
        lyText.setText("");
        damage = getActivity().getIntent();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        value = prefs.getString("sname", null);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("lyrics");


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 lyr = dataSnapshot.child(value).getValue().toString();
                lyText.setText(lyr);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //lyText.setText(value);


       // value1="";

        return view;


    }

}
