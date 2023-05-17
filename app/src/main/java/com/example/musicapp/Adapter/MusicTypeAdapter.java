package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.musicapp.Activity.AcountActivity;
import com.example.musicapp.Activity.SongMusicTypeActivity;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.R;

import java.util.List;

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MyViewHolder> {
    Context context;
    List<MusicType> array;

    public MusicTypeAdapter(Context context, List<MusicType> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_musictype_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView tentheloai;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images =itemView.findViewById(R.id.image_theloai);
//            tentheloai = (TextView) itemView.findViewById(R.id.tvNameTheloai);

        }
    }
    @Override
    public void onBindViewHolder(@NonNull MusicTypeAdapter.MyViewHolder holder, int position) {
        MusicType musicType =array.get(position);
//        holder.tentheloai.setText(musicType.getName());
        Glide.with(context)
                .load(musicType.getThumbnaiUrll())
                .into(holder.images);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Toast.makeText(context,"Bạn đã chọn "+ holder.tenbaihat.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SongMusicTypeActivity.class);
                intent.putExtra("musicTypeId", musicType.getId());
                intent.putExtra("musicTypeImage", musicType.getThumbnaiUrll());
                intent.putExtra("musicTypeName", musicType.getName());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
}
