package com.example.musicapp.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

public class FragmentLyricsNhac extends Fragment {
    private boolean isFragmentAttached = false;
    View view;
    TextView txtLyrics;
    public FragmentLyricsNhac() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lyrics_nhac, container, false);
        txtLyrics=view.findViewById(R.id.txtLyric);
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFragmentAttached = true;
    }
    public void Getloibaihat(String lyrics) {
//        if (isFragmentAttached && txtLyrics == null) {
//            txtLyrics.setText(lyrics);
//            System.out.println("Da co TextView");
//        }
                    txtLyrics.setText(lyrics);

    }
}