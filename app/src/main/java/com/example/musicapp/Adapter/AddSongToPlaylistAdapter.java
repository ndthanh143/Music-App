package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSongToPlaylistAdapter extends BaseAdapter {
    Context context;
    List<Song> array;
    private int layout;
    private String playlistId;

    public AddSongToPlaylistAdapter(Context context,  int layout, List<Song> array, String playlistId) {
        this.context = context;
        this.array = array;
        this.layout = layout;
        this.playlistId = playlistId;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//gọi view chứa layout
            view = inflater.inflate(layout,null);
//ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.tvSongName);
            viewHolder.artist = (TextView) view.findViewById(R.id.tvArtist);
            viewHolder.images = (ImageView) view.findViewById(R.id.ivSongImage);
            viewHolder.btnAddSong = (ImageButton) view.findViewById(R.id.btnAddSongToPlaylist);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Song song = array.get(i);
        viewHolder.name.setText(song.getName());
        viewHolder.artist.setText(song.getArtist());
        Glide.with(context)
                .load(song.getImageSongUrl())
                .into(viewHolder.images);
        viewHolder.btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApiAddSongToPlaylist(playlistId, song);
            }
        });
        return view;
    }

    private void CallApiAddSongToPlaylist(String playlistId, Song song) {
        ApiService.apiService.addSongToPlaylist(playlistId, song).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                Toast.makeText(context, "Added song to playlist!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ViewHolder {
        public ImageView images;
        public TextView name;
        public TextView artist;
        public ImageButton btnAddSong;

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view==null){
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(layout,null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) view.findViewById(R.id.tvSongName);
                viewHolder.artist = (TextView) view.findViewById(R.id.tvArtist);
                viewHolder.images = (ImageView) view.findViewById(R.id.ivSongImage);
                viewHolder.btnAddSong = (ImageButton) view.findViewById(R.id.btnAddSongToPlaylist);
                view.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            return view;
        }
    }

}