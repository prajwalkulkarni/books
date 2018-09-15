package com.example.android.books;



public class Books {

    private String mBookName;
    private String mAuthor;
    private String murl;

    public Books(String bookname,String author,String url){
        mBookName = bookname;
        mAuthor = author;
        murl = url;

    }

    public String getmBookName() {

        return mBookName;
    }

    public String getmAuthor() {

        return mAuthor;
    }
    public String getUrl() {
        return murl;
    }
}
