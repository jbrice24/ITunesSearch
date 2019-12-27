package com.example.itunessearch.ui.songDetail;

import com.example.itunessearch.model.Song;

public interface ISongDetailView {

    void displayMessage(String message);
    void displayTrack(Song track);
}
