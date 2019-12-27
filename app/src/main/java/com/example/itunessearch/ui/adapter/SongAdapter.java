package com.example.itunessearch.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itunessearch.R;
import com.example.itunessearch.helper.Constants;
import com.example.itunessearch.helper.SharedPreferenceHelper;
import com.example.itunessearch.model.Song;
import com.example.itunessearch.ui.songDetail.SongDetailActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {

    private final boolean editFavorites;
    Context context;
    private List<Song> trackList;
    private Song song;
    private List<Song> myFavoriteList = new ArrayList<>();

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout row;
        ImageView imgTrackArtwork, imgFavorite;
        TextView txtTrackName, txtArtistNameNGenre, txtPrice;
        List<Song> myFavoritesSongs = new ArrayList<>();

        MyViewHolder(View view) {
            super(view);
            row = view.findViewById(R.id.song_item_row);
            imgTrackArtwork = view.findViewById(R.id.artwork);
            txtTrackName = view.findViewById(R.id.track_name);
            txtArtistNameNGenre = view.findViewById(R.id.artist_name_and_genre);
            txtPrice = view.findViewById(R.id.price);
            imgFavorite = view.findViewById(R.id.imgFavorite);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(context, SongDetailActivity.class);
                    detail.putExtra("track", trackList.get(getAdapterPosition()));
                    context.startActivity(detail);
                }
            });
        }
    }

    public SongAdapter(Context context, List<Song> trackList, boolean editFavorites) {
        this.context = context;
        this.trackList = trackList;
        this.editFavorites = editFavorites;

        if(trackList == null)
            this.trackList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Song song = trackList.get(position);

        final String artworkUrl = song.getArtworkUrl100();
        Glide.with(context).load(artworkUrl).placeholder(R.drawable.ic_logo).into(holder.imgTrackArtwork);

        if(song.getTrackName() != null)
            holder.txtTrackName.setText(song.getTrackName());

        if(song.getArtistName() != null && song.getPrimaryGenreName() != null)
            holder.txtArtistNameNGenre.setText(song.getArtistName().concat(" | ").concat(song.getPrimaryGenreName()));

        if(song.getTrackPrice() != null)
            holder.txtPrice.setText(String.format("US $ %s", String.valueOf(song.getTrackPrice())));

        if(!editFavorites)
            holder.imgFavorite.setImageResource(R.drawable.ic_yellow_star);

        holder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editFavorites) {
                    if (holder.imgFavorite.getTag() == null) {
                        holder.imgFavorite.setImageResource(R.drawable.ic_yellow_star);
                        holder.imgFavorite.setTag("Favorite");
                        myFavoriteList.add(song);
                        SharedPreferenceHelper.saveString(context, Constants.MY_TRACK_LIST, new Gson().toJson(myFavoriteList));
                    } else {
                        myFavoriteList.remove(song);
                        SharedPreferenceHelper.saveString(context, Constants.MY_TRACK_LIST, new Gson().toJson(myFavoriteList));
                        holder.imgFavorite.setImageResource(R.drawable.ic_favorite_icon_black);
                        holder.imgFavorite.setTag(null);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }
}
