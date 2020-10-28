package com.example.pexeso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void onNewGameButton(View view)
    {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public void onAboutButton(View view)
    {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

}