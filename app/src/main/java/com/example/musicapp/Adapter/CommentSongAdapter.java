package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Activity.PlayMusicActivity;
import com.example.musicapp.Model.Comment;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;
import com.example.musicapp.Service_Local.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentSongAdapter extends RecyclerView.Adapter<CommentSongAdapter.MyViewHolder> {
    private CommentItemClickListener commentItemClickListener;
    Context context;
    List<Comment> array;
    Menu menu;
    Date currentDate, commentCreatedAt,commentUpdatedAt;
    public TextView tvThoigian;
    private boolean isBtnMenuClicked = false;

    private int isAcount=0;


    public CommentSongAdapter(Context context, List<Comment> array ,CommentItemClickListener listener) {
        this.context = context;
        this.array = array;
        this.commentItemClickListener = listener;
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
        public TextView name;
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
        }

    }


    @Override
    public void onBindViewHolder(@NonNull CommentSongAdapter.MyViewHolder holder, int position) {
        Comment commentItem = array.get(position);
        holder.name.setText(commentItem.getUser().getName());
        holder.comment.setText(commentItem.getComment());
        if(commentItem.getUser().getAvatar()!=null){
        Glide.with(context)
                .load(commentItem.getUser().getAvatar())
                .circleCrop()
                .into(holder.image);
        }
        else {
            Glide.with(context)
                    .load(R.drawable.baseline_person_40)
                    .circleCrop()
                    .into(holder.image);
        }
        commentCreatedAt = commentItem.getCreatedAt();
        commentUpdatedAt =commentItem.getUpdatedAt();
        currentDate = new Date();
        if(commentUpdatedAt==null){
            tinhThoiGian(commentCreatedAt,currentDate);
        }
        else {tinhThoiGian(commentUpdatedAt,currentDate);}

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(commentItem.getUser().getId(), SharedPrefManager.getInstance(context).getUser().getId()))
                {
                    PopupMenu popupMenu = new PopupMenu(context, view);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.menu_comment, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int position1 = holder.getAdapterPosition();
                            Comment comment = array.get(position1);

                            switch (item.getItemId()) {
                                case R.id.mnSuaCmt:
                                    // Handle Edit menu item click
                                    String idComment=commentItem.getId();
                                    String txtComment=commentItem.getComment();
                                    commentItemClickListener.onEditCommentClick(idComment,txtComment);
                                    return true;
                                case R.id.mnXoaCmt:
                                    String cmtId=commentItem.getId();
                                    commentItemClickListener.onDeleteCommentClick(cmtId);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                    isAcount=0;
                } else {
                    Toast.makeText(context, "Không có quyền truy cập", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void tinhThoiGian(Date commentCreatedAt, Date currentDate) {
        long timeDifferenceInMillis = currentDate.getTime() - commentCreatedAt.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceInMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceInMillis);
        long days = TimeUnit.MILLISECONDS.toDays(timeDifferenceInMillis);
        if (seconds<=60){
            tvThoigian.setText("vừa xong");
        } else {
            if(minutes<=60){
                tvThoigian.setText(minutes+" phút trước");
            } else {
                if(hours<24){
                    tvThoigian.setText(hours+" giờ trước");
                }
                else tvThoigian.setText(days+" ngày trước");
            }
        }
    }


    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
    public interface CommentItemClickListener {
        void onEditCommentClick(String commentId,String txtcomment);
        void onDeleteCommentClick(String commentId);
    }

}