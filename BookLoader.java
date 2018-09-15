package com.example.android.books;

import android.content.Context;
import android.content.AsyncTaskLoader;

import org.json.JSONException;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Books>> {
    String url;
    public BookLoader(Context context,String url){
        super(context);
        this.url = url;

    }


    @Override
    public List<Books> loadInBackground() {
        if(url == null){
            return null;
        }

        List<Books> list = null;
        try {
            list = QueryUtils.fetchBookData(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    protected  void onStartLoading(){
        forceLoad();
    }
}
