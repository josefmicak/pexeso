package com.example.pexeso;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {

    ImageView card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16;
    ImageView[] IVArray = {card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16};
    int picture1 = R.drawable.antelope;
    int picture2 = R.drawable.chimpanzee;
    int picture3 = R.drawable.elephant;
    int picture4 = R.drawable.giraffe;
    int picture5 = R.drawable.hippopotamus;
    int picture6 = R.drawable.lion;
    int picture7 = R.drawable.rhino;
    int picture8 = R.drawable.zebra;
    int picturePexeso = R.drawable.pexeso;
    List<Integer> values = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8);

    private int firstCardId = 0, secondCardId = 0;
    private int firstClickedCardId = 0, secondClickedCardId = 0;

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

        Collections.shuffle(values);
    }

    public void onButton(View card) throws InterruptedException {
        CharSequence text;
        text = String.valueOf(card);
        String id = ((String) text).substring(text.length() - 3, text.length() - 1);
        int clickedCardId = Integer.valueOf(id);
        int cardId = setPicture(clickedCardId);
        if(firstCardId != 0 && secondCardId == 0)
        {
            if(clickedCardId != firstClickedCardId)
            {
                secondCardId = cardId;
                secondClickedCardId = clickedCardId;
            }
        }
        if(firstCardId == 0)
        {
            firstCardId = cardId;
            firstClickedCardId = clickedCardId;
        }
        if(firstCardId != 0 && secondCardId != 0)
        {

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(firstCardId != secondCardId)
                    {
                        IVArray[firstClickedCardId - 1].setImageResource(R.drawable.pexeso);
                        IVArray[secondClickedCardId - 1].setImageResource(R.drawable.pexeso);
                    }

                    firstCardId = 0;
                    secondCardId = 0;
                    firstClickedCardId = 0;
                    secondClickedCardId = 0;
                }
            }, 1000);
        }
    }

    public int setPicture(int cardId)
    {
        switch(values.get(cardId - 1))
        {
            case 1:
                IVArray[cardId - 1].setImageResource(R.drawable.antelope);
                return 1;
            case 2:
                IVArray[cardId - 1].setImageResource(R.drawable.chimpanzee);
                return 2;
            case 3:
                IVArray[cardId - 1].setImageResource(R.drawable.elephant);
                return 3;
            case 4:
                IVArray[cardId - 1].setImageResource(R.drawable.giraffe);
                return 4;
            case 5:
                IVArray[cardId - 1].setImageResource(R.drawable.hippopotamus);
                return 5;
            case 6:
                IVArray[cardId - 1].setImageResource(R.drawable.lion);
                return 6;
            case 7:
                IVArray[cardId - 1].setImageResource(R.drawable.rhino);
                return 7;
            case 8:
                IVArray[cardId - 1].setImageResource(R.drawable.zebra);
                return 8;
        }
        return 0;
    }
}
