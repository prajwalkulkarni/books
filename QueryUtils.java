package com.example.android.books;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {

    }

    public static List<Books> fetchBookData(String requestUrl) throws JSONException {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonResponse = null;
        URL url = createUrl(requestUrl);
        try {
            jsonResponse = makehttpRequest(url);

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Books> list = parseJson(jsonResponse);
        return list;
    }


    public static URL createUrl(String getUrl) {
        URL url = null;

        try {
            url = new URL(getUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makehttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (jsonResponse == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = parsedata(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;


    }

    private static String parsedata(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();

            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();

            }
        }
        return output.toString();
    }


    private static List<Books> parseJson(String jsonResponse) {
        List<Books> book = new ArrayList<Books>();



        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("items");


            for (int i = 0; i < jsonArray.length(); ++i) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject jsonObject2 = jsonObject1.getJSONObject("volumeInfo");
                 String title = jsonObject2.getString("title");
                JSONArray jsonArray1 = jsonObject2.getJSONArray("authors");
                JSONObject saleInfo = jsonObject1.getJSONObject("saleInfo");
                 String author = jsonArray1.getString(i);
                String url = saleInfo.getString("buyLink");
                book.add(new Books(title, author,url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }





        return book;

    }
}



