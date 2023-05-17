package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.DTO.PlaylistDTO;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlaylistActivity extends AppCompatActivity {
    private Button btnCreate;
    ImageButton btnClose;
    private EditText edtPlaylistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);
        anhxa();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playlistName = edtPlaylistName.getText().toString();
                PlaylistDTO dto = new PlaylistDTO();
                dto.setName(playlistName);
                dto.setUser(null);
                CallApiCreatePlaylist(dto);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatePlaylistActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CallApiCreatePlaylist(PlaylistDTO playlistDTO) {
        ApiService.apiService.createPlaylist(playlistDTO).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                String playlistId = response.body().getId();
                Intent i = new Intent(CreatePlaylistActivity.this, PlaylistAcitivity.class);
                i.putExtra("playlistId", playlistId);
                startActivity(i);
                Toast.makeText(CreatePlaylistActivity.this, "Tạo playlist thành công" + playlistId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText(CreatePlaylistActivity.this, "Call API Error" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void anhxa() {
        btnCreate = (Button) findViewById(R.id.btnCreatePlaylist);
        edtPlaylistName = (EditText) findViewById(R.id.edtPlaylistName);
        btnClose = findViewById(R.id.imageView11);
    }
}