package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Activity.PlayMusicActivity;
import com.example.musicapp.Model.Comment;
import com.example.musicapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CommentSongAdapter extends RecyclerView.Adapter<CommentSongAdapter.MyViewHolder> {
    Context context;
    List<Comment> array;
    Menu menu;

    public CommentSongAdapter(Context context, List<Comment> array ) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_comment_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name,tvThoigian;
        public TextView comment;
        public ImageButton btnMenu;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.ivUser);
            name = (TextView) itemView.findViewById(R.id.tvTenUser);
            comment = itemView.findViewById(R.id.tvComment);
            tvThoigian = itemView.findViewById(R.id.tvThoigian);
            btnMenu= itemView.findViewById(R.id.btnOptionCmt);
//            btnMenu.setOnClickListener(this);\
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view);
                }
            });
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(context, view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_comment, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int position = getAdapterPosition();
                    Comment comment = array.get(position);

                    switch (item.getItemId()) {
                        case R.id.mnSuaCmt:
                            // Handle Edit menu item click
                            String idComment=comment.getId();
//                            Intent intent = new Intent(context, PlayMusicActivity.class);
//                intent.putExtra("songId", song.getId());
//                            Bundle bundle = new Bundle();
//                            bundle.putString("comment", comment.getComment());
//                            bundle.putString("commentId", comment.getId());
//                            intent.putExtras(bundle);
//                            itemView.getContext().startActivity(intent);
                            return true;
                        case R.id.mnXoaCmt:
                            // Handle Delete menu item click
                            // ...
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }

    }


    @Override
    public void onBindViewHolder(@NonNull CommentSongAdapter.MyViewHolder holder, int position) {
        Comment commentItem = array.get(position);

        holder.name.setText(commentItem.getUser().getName());
        holder.comment.setText(commentItem.getComment());
        Date commentCreatedAt = commentItem.getCreatedAt();
                    Date currentDate = new Date();
        long timeDifferenceInMillis = currentDate.getTime() - commentCreatedAt.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceInMillis);
        long days = TimeUnit.MILLISECONDS.toDays(timeDifferenceInMillis);
        if (seconds<=60){
            holder.tvThoigian.setText(seconds+" giây trước");
        } else {
            if(minutes<=60){
                holder.tvThoigian.setText(minutes+" phút trước");
            } else {
                if(hours<24){
                    holder.tvThoigian.setText(hours+" giờ trước");
                }
                else holder.tvThoigian.setText(days+" ngày trước");
            }
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
}