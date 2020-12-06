package com.example.pexeso;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class UserHighScores extends Fragment {

    ListView listView;
    List<UserHighScore> bestUserScores = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.user_high_scores, container, false);
        listView = rootView.findViewById(R.id.listView);
        loadBestUserResults();
        return rootView;
    }

    public void loadBestUserResults()
    {
       /* SharedPreferences sh = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String moveAmounts = sh.getString("moveCounter", "");
        String[] splitString = moveAmounts.split(";");
        for(int i = 0; i < splitString.length; i++)
        {
            if(splitString[i] == null)
            {
                break;
            }
            bestUserResults.add(i, String.valueOf(i + 1) + ". " + splitString[i] + " moves");
        }*/
        DatabaseHandler db = new DatabaseHandler(getActivity());
        bestUserScores = db.getAllScores();

        ArrayAdapter<UserHighScore> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, bestUserScores);
        listView.setAdapter(arrayAdapter);
    }
}