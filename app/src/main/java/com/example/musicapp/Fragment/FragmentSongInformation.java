package com.example.musicapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Activity.PlayMusicActivity;
import com.example.musicapp.Adapter.PlayNhacAdapter;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;

import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link fragment_list_song_playnhac#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FragmentSongInformation extends Fragment {
    View view;
    private ImageView ivSongImage;
    private TextView tvSongName, tvSongArtist, tvSongAlbum, tvSongType, tvSongProvider;
    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_song_information, container, false);
        anhXa();
        return view;
    }

    public void LoadInformation(Song song) {
        Glide.with(getContext())
                .load(song.getImageSongUrl())
                .into(ivSongImage);
        tvSongName.setText(song.getName());
        tvSongAlbum.setText("Love song");
        tvSongType.setText(song.getType().getName());
        tvSongProvider.setText("HarmoniQ");
        tvSongArtist.setText(song.getArtist());
    }

    private void anhXa() {
        ivSongImage = view.findViewById(R.id.ivSongInformationImage);
        tvSongName = view.findViewById(R.id.tvSongInformationName);
        tvSongAlbum = view.findViewById(R.id.tvSongInformationAlbum);
        tvSongArtist = view.findViewById(R.id.tvSongInformationArtist);
        tvSongProvider = view.findViewById(R.id.tvSongInformationProvider);
        tvSongType = view.findViewById(R.id.tvSongInformationType);
    }


}