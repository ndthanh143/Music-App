//package com.example.musicapp.Fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.musicapp.Model.Song;
//import com.example.musicapp.R;
//import com.example.musicapp.Service_API.ApiService;
//import com.example.musicapp.Service_API.DataService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class FragmentBaiHat extends Fragment {
//    View view;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_bai_hat,container,false);
//        GetData();
//        return view;
//    }
//
//    private void GetData() {
//        DataService dataService = ApiService.getService();
//        Call<List<Song>> callback = dataService.getListRandomSong();
//        callback.enqueue(new Callback<List<Song>>() {
//            @Override
//            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
//                ArrayList<Song> mSongs=(ArrayList<Song>) response.body();
//                System.out.println(mSongs.get(0).getName());
//            }
//
//            @Override
//            public void onFailure(Call<List<Song>> call, Throwable t) {
//
//            }
//        });
//    }
//}
