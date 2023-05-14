package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;

import java.util.List;

public class BaiHatAdapter extends RecyclerView.Adapter<BaiHatAdapter.MyViewHolder> {
    Context context;
    List<Song> array;

    public BaiHatAdapter(Context context, List<Song> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_song_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView tenbaihat,tencasi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.imageViewBaihat);
            tenbaihat = (TextView) itemView.findViewById(R.id.textviewTenbaihat);
            tencasi = (TextView) itemView.findViewById(R.id.textviewTencasi);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull BaiHatAdapter.MyViewHolder holder, int position) {
        Song song =array.get(position);
        holder.tenbaihat.setText(song.getName());
        holder.tencasi.setText(song.getArtist());
        Glide.with(context)
                .load(song.getImageSongUrl())
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
}
