package com.example.itunessearch.ui.searchList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.itunessearch.R;
import com.example.itunessearch.model.Song;
import com.example.itunessearch.ui.adapter.SongAdapter;
import com.example.itunessearch.ui.favoriteSong.FavoriteSongActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity implements ISearchView{

    private Context context;
    private LinearLayout main;
    private TextView txtNoSongs;
    private ShimmerRecyclerView listTracks;

    private List<Song> dataTracks = new ArrayList<>();
    private SongAdapter adapter;

    ISearchImpl presenter;

    public SearchListActivity() {
        presenter = new SearchListPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        initViews();
        initAdapter();

    }

    private void initViews(){
        main = findViewById(R.id.song_list_main);
        txtNoSongs  = findViewById(R.id.txtNoSongs);
        listTracks = findViewById(R.id.listSongs);
    }

    private void initAdapter(){
        adapter = new SongAdapter(context, dataTracks, true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listTracks.setLayoutManager(mLayoutManager);
        listTracks.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        listTracks.setItemAnimator(new DefaultItemAnimator());
        listTracks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        initSearchView(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorites:
                Intent intent = new Intent(this, FavoriteSongActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSearchView(Menu menu){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void search(final String strTerm) {
        txtNoSongs.setVisibility(View.GONE);
        listTracks.setVisibility(View.VISIBLE);

        dataTracks.clear();
        adapter.notifyDataSetChanged();

        setLoadingIndicator(true);
        presenter.getTracks(strTerm);
    }

    @Override
    public void displayTracks(List<Song> dataTracks) {
        setLoadingIndicator(false);
        this.dataTracks.clear();
        this.dataTracks.addAll(dataTracks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayMessage(String message) {
        setLoadingIndicator(false);
        Snackbar.make(main, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        if (isLoading) {
            listTracks.showShimmerAdapter();
        } else {
            listTracks.hideShimmerAdapter();
        }
    }



}
