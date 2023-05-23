package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.savedstate.SavedStateRegistry;

import com.bumptech.glide.Glide;
import com.example.musicapp.Activity.ListPlaylistActivity;
import com.example.musicapp.Activity.PlaylistAcitivity;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPlaylistAdapter extends RecyclerView.Adapter<ListPlaylistAdapter.MyViewHolder> {
    Context context;
    List<Playlist> array;

    public ListPlaylistAdapter(Context context, List<Playlist> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_playlist_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView textviewTenPlaylist,textviewSizePlaylist;
        public ImageView btnDeletePlaylist;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images =itemView.findViewById(R.id.imageViewPlaylist);
            textviewTenPlaylist =itemView.findViewById(R.id.textviewTenPlaylist);
            textviewSizePlaylist =itemView.findViewById(R.id.textviewSizePlaylist);
            btnDeletePlaylist = itemView.findViewById(R.id.btnDeletePlaylist);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ListPlaylistAdapter.MyViewHolder holder, int position) {
        Playlist Playlist =array.get(position);
        holder.textviewTenPlaylist.setText(Playlist.getName());
        holder.textviewSizePlaylist.setText(Playlist.getListSongs().size()+" bài hát");
        Glide.with(context)
                .load(Playlist.getThumbnail())
                .into(holder.images);
        holder.btnDeletePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.deletePlaylist(Playlist.getId()).enqueue(new Callback<com.example.musicapp.Model.Playlist>() {
                    @Override
                    public void onResponse(Call<com.example.musicapp.Model.Playlist> call, Response<com.example.musicapp.Model.Playlist> response) {
                        Toast.makeText(context, "Đã xoá playlist", Toast.LENGTH_SHORT).show();
                        List<Playlist> newList = new ArrayList<>();
                        for(int i = 0 ; i < array.size(); i++) {
                            if(array.get(i).getId().equals(response.body().getId())) {
                                array.remove(i);
                            }
                            notifyDataSetChanged();
                        }
                     }

                    @Override
                    public void onFailure(Call<com.example.musicapp.Model.Playlist> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Toast.makeText(context,"Bạn đã chọn "+ holder.tenbaihat.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PlaylistAcitivity.class);
                intent.putExtra("playlistId", Playlist.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
}
