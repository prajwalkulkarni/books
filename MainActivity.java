package com.example.android.books;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {


    public static String BOOK_URL = " ";

    Button button;
    BookAdapter bookAdapter;
    ListView listView;
    EditText searchItem;
    TextView bookname;
    TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchItem = findViewById(R.id.search_bar);
        bookname = findViewById(R.id.book_name);
        author = findViewById(R.id.author);
        button = findViewById(R.id.button);

        listView = findViewById(R.id.list_item);

        bookAdapter = new BookAdapter(this, new ArrayList<Books>());
        listView.setAdapter(bookAdapter);

        ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!= null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader.
            loaderManager.initLoader(1, null, this);
        }else {
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (networkInfo != null && networkInfo.isConnected()) {
                    bookAdapter.clear();

                    updateUrl(searchItem.getText().toString());

                    restartLoader();
                }else{
                    bookAdapter.clear();
                }
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Books currentBooklist = bookAdapter.getItem(position);

                Uri uri = Uri.parse(currentBooklist.getUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);

                startActivity(intent);
            }
        });


    }

    private String updateUrl(String searchValue){
        if (searchValue.contains(" ")){
            searchValue = searchValue.replace(" ","+");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.googleapis.com/books/v1/volumes?q=").append(searchValue).append("&filter=ebooks&orderBy=relevance&maxResults=20");
        BOOK_URL = stringBuilder.toString();
        return BOOK_URL;
    }

    @Override
    public Loader<List<Books>> onCreateLoader(int id, Bundle args) {

        updateUrl(searchItem.getText().toString());
        return new BookLoader(MainActivity.this,BOOK_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> booklists) {



        bookAdapter.clear();

        if (booklists != null && !booklists.isEmpty()) {
            bookAdapter.addAll(booklists);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        bookAdapter.clear();
    }

    public void restartLoader(){
        getLoaderManager().restartLoader(1,null,MainActivity.this);
    }


}


// <--------------------------------------------------------------------------------------------------------------------------------------------->
