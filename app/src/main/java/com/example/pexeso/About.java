package com.example.pexeso;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setTheme();

        TextView textView = findViewById(R.id.aboutText);
        textView.setText("This game has been created as a semestral project for the subject Operating Systems for Mobile Devices II of the Faculty of Electrical Engineering and Computer Science of VŠB-TUO." +
                "\nStudent: Josef Micak / MIC0378" +
                "\nWinter semester of AY 2020/2021");
    }

    public void setTheme()
    {
        int[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.BLACK, Color.GRAY, Color.rgb(139, 69, 19), Color.MAGENTA};
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundColor(colors[sharedPreferences.getInt("themeColor", 0)]);

        TextView aboutText = findViewById(R.id.aboutText);
        aboutText.setTextColor(colors[sharedPreferences.getInt("themeColor", 0)]);
        Button returnToMenuButton = findViewById(R.id.returnToMenuButton);
        returnToMenuButton.setTextColor(colors[sharedPreferences.getInt("themeColor", 0)]);
    }

    public void onreturnToMenuButton(View view)
    {
        finish();
    }
}
