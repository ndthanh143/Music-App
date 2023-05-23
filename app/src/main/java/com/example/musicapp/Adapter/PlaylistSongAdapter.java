package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Activity.PlayMusicActivity;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistSongAdapter extends RecyclerView.Adapter<PlaylistSongAdapter.MyViewHolder> {
    Context context;
    List<Song> array;
    String playlistId;

    public PlaylistSongAdapter(Context context, List<Song> array, String playlistId ) {
        this.context = context;
        this.array = array;
        this.playlistId = playlistId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_song_in_playlist_display, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name;
        public TextView artist;
        public ImageView btnRemove;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.ivSongImage);
            name = (TextView) itemView.findViewById(R.id.tvSongName);
            artist = (TextView) itemView.findViewById(R.id.tvArtist);
            btnRemove = (ImageView) itemView.findViewById(R.id.btnRemoveSongFromPlaylist);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull PlaylistSongAdapter.MyViewHolder holder, int position) {
        Song song = array.get(position);

        holder.name.setText(song.getName());
        holder.artist.setText(song.getArtist());
        Glide.with(context)
                .load(song.getImageSongUrl())
                .into(holder.image);
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.removeSongFromPlaylist(playlistId, song).enqueue(new Callback<Playlist>() {
                    @Override
                    public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                        Toast.makeText(context, "Đã xoá bài nhạc khỏi playlist", Toast.LENGTH_SHORT).show();
                        List<Song> newList = new ArrayList<>();
                        for(int i = 0 ; i < array.size(); i++) {
                            if(array.get(i).getId().equals(song.getId())) {
                                array.remove(i);
                            }
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Playlist> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<String> listSong= new ArrayList<>();
                for(int i = 0; i < array.size(); i++) {
                    listSong.add(array.get(i).getId());
                }
                bundle.putStringArrayList("listSong", listSong);
                bundle.putString("songId", song.getId());
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
}