package com.example.pexeso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        setTheme();
    }

    public void setTheme()
    {
        int[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.BLACK, Color.GRAY, Color.rgb(139, 69, 19), Color.MAGENTA};
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundColor(colors[sharedPreferences.getInt("themeColor", 0)]);

        ViewGroup layout = (ViewGroup)findViewById(R.id.constraintLayout);
        for (int i = 0; i < layout.getChildCount(); i++) {

            View child = layout.getChildAt(i);
            if(child instanceof Button)
            {
                Button button = (Button) child;
                button.setTextColor(colors[sharedPreferences.getInt("themeColor", 0)]);
            }

        }
    }

    public void onNewGameButton(View view)
    {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public void onHighScoresButton(View view)
    {
        Intent intent = new Intent(this, MainHighScores.class);
        startActivity(intent);
    }

    public void onSettingsButton(View view)
    {
        Intent intent = new Intent(this, Settings.class);
        startActivityForResult(intent, 1);
    }


    public void onAboutButton(View view)
    {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void onExitButton(View view)
    {
        System.exit(1);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            setTheme();
        }
    }

}