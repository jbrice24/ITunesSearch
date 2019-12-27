package com.example.itunessearch.ui.favoriteSong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.itunessearch.R;
import com.example.itunessearch.helper.SharedPreferenceHelper;
import com.example.itunessearch.ui.adapter.SongAdapter;

public class FavoriteSongActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SongAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_song);
        initList();
    }

    private void initList() {
        mRecyclerView = findViewById(R.id.reyclerViewUser);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SongAdapter(this, SharedPreferenceHelper.getFavoritesTracks(this), false);
        mRecyclerView.setAdapter(mAdapter);

    }
}
