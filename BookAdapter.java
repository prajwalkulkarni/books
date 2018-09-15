package com.example.android.books;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Books> {
    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Activity context, ArrayList<Books> books){
        super(context,0,books);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Books books = getItem(position);

        TextView textView = (TextView)listItemView.findViewById(R.id.book_name);
        textView.setText(books.getmBookName());

        TextView textView1 = (TextView)listItemView.findViewById(R.id.author);
        textView1.setText(books.getmAuthor());





        return listItemView;
    }
}
