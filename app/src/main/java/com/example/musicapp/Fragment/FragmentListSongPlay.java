package com.example.musicapp.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class FragmentListSongPlay extends Fragment {
    View view;
    RecyclerView recyclerViewplaynhac;
    PlayNhacAdapter playNhacAdapter;
    ArrayList<SongDTO> songList;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_song_playnhac, container, false);
        recyclerViewplaynhac = view.findViewById(R.id.recycleviewplay);
        // Inflate the layout for this fragment
        // Convert from Parcelable ArrayList to Song ArrayList
        songList = new ArrayList<>();
        for (SongDTO p : PlayMusicActivity.mangbaihat) {
            songList.add((SongDTO) p);
        }
//        if (songList.size() > 0) {
//            playNhacAdapter = new PlayNhacAdapter(getActivity(), songList);
//            recyclerViewplaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerViewplaynhac.setAdapter(playNhacAdapter);
//        }
        return view;
    }
}