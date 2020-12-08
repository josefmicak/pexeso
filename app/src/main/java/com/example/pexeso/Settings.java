package com.example.pexeso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Settings extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton animalsButton;
    RadioButton carsButton;
    private String pictureSet;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setTheme();
        radioGroup = findViewById(R.id.radioGroup);
        animalsButton = findViewById(R.id.Animals);
        carsButton = findViewById(R.id.Cars);
        initializeSpinner();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        toggleRadioButton(sh.getString("pictureSet", "Animals"));
    }

    public void setTheme()
    {
        int[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.BLACK, Color.GRAY, Color.rgb(139, 69, 19), Color.MAGENTA};
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundColor(colors[sharedPreferences.getInt("themeColor", 0)]);

        TextView pictureText = findViewById(R.id.pictureText);
        TextView themeText = findViewById(R.id.themeText);
        pictureText.setTextColor(colors[sharedPreferences.getInt("themeColor", 0)]);
        themeText.setTextColor(colors[sharedPreferences.getInt("themeColor", 0)]);
        Button saveButton = findViewById(R.id.returnToMenuButton);
        saveButton.setTextColor(colors[sharedPreferences.getInt("themeColor", 0)]);
    }

    public void toggleRadioButton(String pictureSetValue)
    {
        pictureSet = pictureSetValue;
        if(pictureSetValue.equals("Animals"))
        {
            animalsButton.setChecked(true);
            carsButton.setChecked(false);
        }
        else
        {
            animalsButton.setChecked(false);
            carsButton.setChecked(true);
        }
    }

    public void checkButton(View view)
    {
        String text = String.valueOf(view);
        String buttonValue = (text).substring(text.length() - 5, text.length() - 1);
        if(buttonValue.equals("mals"))
        {
            buttonValue = "Animals";
        }
        toggleRadioButton(buttonValue);
    }

    public void initializeSpinner()
    {

        dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Blue", "Red", "Green", "Cyan", "Black", "Gray", "Brown", "Magenta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        dropdown.setSelection(sharedPreferences.getInt("themeColor", 0));
    }

    public void onSaveButton(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("pictureSet", pictureSet);
        myEdit.putInt("themeColor", (int) dropdown.getSelectedItemId());
        myEdit.commit();
        Toast.makeText(this, "Settings have been succesfully saved.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }
}
