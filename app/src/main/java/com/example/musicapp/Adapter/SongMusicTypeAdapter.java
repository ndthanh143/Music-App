package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Activity.PlayMusicActivity;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;

import java.util.List;

public class SongMusicTypeAdapter extends RecyclerView.Adapter<SongMusicTypeAdapter.MyViewHolder>{
    Context context;
    List<Song> array;

    public SongMusicTypeAdapter(Context context, List<Song> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public SongMusicTypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_song_item, null);
        SongMusicTypeAdapter.MyViewHolder myViewHolder = new SongMusicTypeAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name;
        public TextView artist;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageViewBaihat);
            name = (TextView) itemView.findViewById(R.id.textviewTenbaihat);
            artist = (TextView) itemView.findViewById(R.id.textviewTencasi);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull SongMusicTypeAdapter.MyViewHolder holder, int position) {
        Song Song = array.get(position);

        holder.name.setText(Song.getName());
        holder.artist.setText(Song.getArtist());
        Glide.with(context)
                .load(Song.getImageSongUrl())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("SongId", Song.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
}
