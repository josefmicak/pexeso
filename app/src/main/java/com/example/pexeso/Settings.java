package com.example.pexeso;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton animalsButton;
    RadioButton carsButton;
    private String pictureSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        radioGroup = findViewById(R.id.radioGroup);
        animalsButton = findViewById(R.id.Animals);
        carsButton = findViewById(R.id.Cars);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        toggleRadioButton(sh.getString("pictureSet", "Animals"));
    }

    public void toggleRadioButton(String pictureSetValue)
    {
        pictureSet = pictureSetValue;
        if(pictureSetValue == "Animals")
        {
            Toast.makeText(this, pictureSetValue, Toast.LENGTH_LONG).show();
            animalsButton.setChecked(true);
            carsButton.setChecked(false);
        }
        else
        {
            Toast.makeText(this, pictureSetValue, Toast.LENGTH_LONG).show();
            animalsButton.setChecked(false);
            carsButton.setChecked(true);
        }
    }

    public void checkButton(View view)
    {
        String text = String.valueOf(view);
        String buttonValue = ((String) text).substring(text.length() - 5, text.length() - 1);
        if(buttonValue.equals("mals"))
        {
            buttonValue = "Animals";
        }
        toggleRadioButton(buttonValue);
    }

    public void onSaveButton(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("pictureSet", pictureSet);
        myEdit.commit();
        finish();
    }
}
