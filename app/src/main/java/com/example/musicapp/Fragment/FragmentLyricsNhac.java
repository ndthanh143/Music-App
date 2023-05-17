package com.example.musicapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicapp.R;

public class FragmentLyricsNhac extends Fragment {
    private boolean isFragmentAttached = false;
    View view;
    TextView txtLyrics;
    public FragmentLyricsNhac() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lyrics_nhac, container, false);
        anhXa();
        return view;
    }

    private void anhXa() {
        txtLyrics= view.findViewById(R.id.tvSongLyrics);
    }

    public void LoadLyrics(String lyrics) {
        System.out.println("Lyrics: "+lyrics);
        txtLyrics.setText(lyrics);
    }

}