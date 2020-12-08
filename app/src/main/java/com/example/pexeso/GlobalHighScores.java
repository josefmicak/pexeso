package com.example.pexeso;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GlobalHighScores extends Fragment {

    ListView listView;
    List<GlobalHighScore> bestGlobalScores = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.global_high_scores, container, false);
        listView = rootView.findViewById(R.id.listView);
        loadBestGlobalResults();
        return rootView;
    }

    public void loadBestGlobalResults()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String getResultsUrl = "http://tamztest.8u.cz/results.txt";
                    URL url = new URL(getResultsUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String downloadedContent = "",data="";

                    while ((data = reader.readLine()) != null){
                        downloadedContent  += data + "\n";
                    }

                    String[] splitByNewLine = downloadedContent.split("\n");

                    for(String line : splitByNewLine)
                    {
                        String[] splitBySemicolon = line.split(";");
                        GlobalHighScore globalHighScore = new GlobalHighScore(Integer.valueOf(splitBySemicolon[0]), splitBySemicolon[1], splitBySemicolon[2]);
                        bestGlobalScores.add(globalHighScore);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<GlobalHighScore> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, bestGlobalScores);
                listView.setAdapter(arrayAdapter);
            }
        }, 1000);
    }
}