package com.example.pexeso;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Game extends AppCompatActivity {

    ImageView card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16, soundImage;
    ImageView[] IVArray = {card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16};
    Boolean[] isFlipped = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    TextView remainingText, movesText;
    private List<Integer> values = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8);

    private int firstCardId = 0, secondCardId = 0;
    private int firstClickedCardId = 0, secondClickedCardId = 0;
    private int remaining = 16, moves = 0;
    private List<Integer> bestResults = new ArrayList<Integer>();
    private Boolean soundEnabled = true;
    MediaPlayer clickSound, successSound, failSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        loadScores();

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
        soundImage = findViewById(R.id.soundButton);

        Collections.shuffle(values);

        remainingText = findViewById(R.id.remainingTV);
        remainingText.setText("Remaining: " + remaining);
        movesText = findViewById(R.id.movesTV);
        movesText.setText("Moves: " + moves);

        clickSound = MediaPlayer.create(this, R.raw.click);
        successSound = MediaPlayer.create(this, R.raw.success);
        failSound = MediaPlayer.create(this, R.raw.fail);
    }

    public void loadScores()
    {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String moveAmounts = sh.getString("moveCounter", "");
        String[] splitString = moveAmounts.split(";");
        for(int i = 0; i < splitString.length; i++)
        {
            if(splitString[i] == null || splitString[i] == "")
            {
                break;
            }
            bestResults.add(i, Integer.valueOf(splitString[i]));
        }
    }

    public void saveScores()
    {
        String moveAmounts = "";
        for(int i = 0; i < bestResults.size(); i++)
        {
            moveAmounts = moveAmounts + String.valueOf(bestResults.get(i)) + ";";
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("moveCounter", moveAmounts);

        myEdit.commit();
    }

    public void onButton(View card) throws InterruptedException {
        String text = String.valueOf(card);
        String id = ((String) text).substring(text.length() - 3, text.length() - 1);
        int clickedCardId = Integer.valueOf(id);
        int cardId = setPicture(clickedCardId);
        if (firstCardId != 0 && secondCardId == 0) {
            if (clickedCardId != firstClickedCardId && !isFlipped[clickedCardId - 1]) {
                playSound(1);
                secondCardId = cardId;
                secondClickedCardId = clickedCardId;

                moves++;
                movesText = findViewById(R.id.movesTV);
                movesText.setText("Moves: " + moves);
            }
        }
        if (firstCardId == 0 && !isFlipped[clickedCardId - 1]) {
            playSound(1);
            firstCardId = cardId;
            firstClickedCardId = clickedCardId;

            moves++;
            movesText = findViewById(R.id.movesTV);
            movesText.setText("Moves: " + moves);
        }
        if (firstCardId != 0 && secondCardId != 0) {
            if (firstCardId == secondCardId) {
                remaining = remaining - 2;
                remainingText = findViewById(R.id.remainingTV);
                remainingText.setText("Remaining: " + remaining);
                isFlipped[firstClickedCardId - 1] = true;
                isFlipped[secondClickedCardId - 1] = true;
                playSound(2);

                if (remaining == 0) {
                    endGame();
                }
            }
            for (int i = 0; i < IVArray.length; i++) {
                IVArray[i].setClickable(false);
            }
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (firstCardId != secondCardId) {
                        IVArray[firstClickedCardId - 1].setImageResource(R.drawable.pexeso);
                        IVArray[secondClickedCardId - 1].setImageResource(R.drawable.pexeso);
                        playSound(3);
                    }

                    firstCardId = 0;
                    secondCardId = 0;
                    firstClickedCardId = 0;
                    secondClickedCardId = 0;
                    for (int i = 0; i < IVArray.length; i++) {
                        IVArray[i].setClickable(true);
                    }
                }
            }, 1000);
        }
    }

    public int setPicture(int cardId) {
        switch (values.get(cardId - 1)) {
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

    public void endGame() {
        saveScore();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Congratulations, you win!\nNumber of moves: " + moves);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Return to main menu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void saveScore()
    {
        if(bestResults.size() == 0)
        {
            bestResults.add(10000);
        }
        bestResults.add(moves);
        bestResults.sort(Comparator.<Integer>naturalOrder());

        if(bestResults.size() == 2 && bestResults.get(1) == 10000)
        {
            bestResults.remove(1);
        }

        if(bestResults.size() == 6)
        {
            bestResults.remove(5);
        }
        saveScores();
    }

    public void playSound(int soundId)
    {
        if(soundEnabled)
        {
            switch (soundId)
            {
                case 1:
                    clickSound.start();
                    break;
                case 2:
                    successSound.start();
                    break;
                case 3:
                    failSound.start();
                    break;
            }
        }
    }

    public void toggleSound(View v)
    {
        soundEnabled = !soundEnabled;
        if(soundEnabled)
        {
            soundImage.setImageResource(R.drawable.soundon);
        }
        else
        {
            soundImage.setImageResource(R.drawable.soundoff);
        }
    }
}
