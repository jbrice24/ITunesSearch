package com.example.itunessearch.ui.songDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.itunessearch.R;
import com.example.itunessearch.model.Song;
import com.google.android.material.snackbar.Snackbar;

public class SongDetailActivity extends AppCompatActivity implements ISongDetailView {

    Context context;
    LinearLayout main;
    ImageView imgArtwork;
    TextView tvArtistName, tvGenre, tvPrice;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        context = SongDetailActivity.this;
        initViews();
        initIntent();

    }

    private void initIntent() {
        try {
            displayTrack((Song) getIntent().getSerializableExtra("track"));
        } catch (Exception e) {
            displayMessage(getString(R.string.error_display));
        }
    }

    private void initViews(){
        main = findViewById(R.id.song_detail_main);
        imgArtwork = findViewById(R.id.imgArtworkDetail);
        tvArtistName = findViewById(R.id.artist_name_detail);
        tvGenre = findViewById(R.id.genre_detail);
        tvPrice = findViewById(R.id.price_detail);
        videoView = findViewById(R.id.videoView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void displayMessage(String message) {
        Snackbar.make(main, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void displayTrack(Song track) {

        initToolbar(track);
        initImage(track);

        tvArtistName.setText(track.getArtistName());
        tvGenre.setText(track.getPrimaryGenreName());

        if(track.getTrackPrice() != null)
            tvPrice.setText(String.format(getString(R.string.us), String.valueOf(track.getTrackPrice())));

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri video = Uri.parse(track.getPreviewUrl());
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.start();
    }

    private void initToolbar(Song track){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(track.getTrackName());
        }
    }

    private void initImage(Song track){
        String artworkUrl = track.getArtworkUrl100();
        Glide.with(context).load(artworkUrl).placeholder(R.drawable.ic_logo).into(imgArtwork);
    }
}
