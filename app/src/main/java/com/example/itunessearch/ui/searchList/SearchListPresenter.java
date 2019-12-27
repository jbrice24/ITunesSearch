package com.example.itunessearch.ui.searchList;

import com.example.itunessearch.R;
import com.example.itunessearch.model.ApiResponse;
import com.example.itunessearch.service.ApiManager;
import com.example.itunessearch.service.ServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchListPresenter implements ISearchImpl{
    private ISearchView songListView;
    private String search;
    SearchListPresenter(ISearchView songListView) {
        this.songListView = songListView;
    }

    @Override
    public void getTracks(String term) {
        search = term;
        ApiManager service = ServiceFactory.getInstance();
        Call<ApiResponse> call = service.getTracks(term);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse trackModel = response.body();
                if (trackModel.getResultCount() > 0)
                    songListView.displayTracks(trackModel.getTracks());
                else
                    songListView.displayMessage(songListView.getContext().getString(R.string.not_results).concat(search));

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                songListView.displayMessage("No songs found result for ".concat(search));
            }
        });
    }
}
