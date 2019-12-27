package com.example.itunessearch.ui.searchList;

import android.content.Context;

import com.example.itunessearch.model.Song;

import java.util.List;

public interface ISearchView {

    void displayMessage(String message);

    void setLoadingIndicator(boolean isLoading);

    void displayTracks(List<Song> dataTracks);

    Context getContext();
}
