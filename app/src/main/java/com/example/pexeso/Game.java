package com.example.pexeso;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

    ImageView card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16;
    ImageView[] IVArray = {card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16};
    int[] values = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        IVArray[0] = findViewById(R.id.card01);
        IVArray[1] = findViewById(R.id.card02);
        IVArray[2] = findViewById(R.id.card03);
        IVArray[3] = findViewById(R.id.card04);
        IVArray[4] = findViewById(R.id.card05);
        IVArray[5] = findViewById(R.id.card06);
        IVArray[6] = findViewById(R.id.card07);
        IVArray[7] = findViewById(R.id.card08);
        IVArray[8] = findViewById(R.id.card09);
        IVArray[9] = findViewById(R.id.card10);
        IVArray[10] = findViewById(R.id.card11);
        IVArray[11] = findViewById(R.id.card12);
        IVArray[12] = findViewById(R.id.card13);
        IVArray[13] = findViewById(R.id.card14);
        IVArray[14] = findViewById(R.id.card15);
        IVArray[15] = findViewById(R.id.card16);
    }

    public void onButton(View card)
    {
        CharSequence text;
        text = String.valueOf(card);
        String id = ((String) text).substring(text.length() - 3, text.length() - 1);
        int cardId = Integer.valueOf(id);
        setPicture(cardId);
    }

    public void setPicture(int cardId)
    {
        switch(values[cardId - 1])
        {
            case 1:
                IVArray[cardId - 1].setImageResource(R.drawable.antelope);
                break;
            case 2:
                IVArray[cardId - 1].setImageResource(R.drawable.chimpanzee);
                break;
            case 3:
                IVArray[cardId - 1].setImageResource(R.drawable.elephant);
                break;
            case 4:
                IVArray[cardId - 1].setImageResource(R.drawable.giraffe);
                break;
            case 5:
                IVArray[cardId - 1].setImageResource(R.drawable.hippopotamus);
                break;
            case 6:
                IVArray[cardId - 1].setImageResource(R.drawable.lion);
                break;
            case 7:
                IVArray[cardId - 1].setImageResource(R.drawable.rhino);
                break;
            case 8:
                IVArray[cardId - 1].setImageResource(R.drawable.zebra);
                break;
        }
    }
}
