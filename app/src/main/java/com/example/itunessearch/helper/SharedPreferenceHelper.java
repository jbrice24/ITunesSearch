package com.example.itunessearch.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.itunessearch.model.Song;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferenceHelper {

    public static List<Song> getFavoritesTracks(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        Type listType = new TypeToken<List<Song>>() {}.getType();

        return new Gson().fromJson(sharedpreferences.getString(Constants.MY_TRACK_LIST, ""), listType);

    }

    public static void saveString(Context context, String key, String value){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedpreferences.edit();
        myEdit.putString(key, value);
        myEdit.commit();

    }
}
