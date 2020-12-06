package com.example.pexeso;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Game extends AppCompatActivity {
    WebView webView;
    ImageView card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16, soundImage;
    ImageView[] IVArray = {card01, card02, card03, card04, card05, card06, card07, card08, card09, card10, card11, card12, card13, card14, card15, card16};
    Boolean[] isFlipped = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    TextView remainingText, movesText;
    private List<Integer> values = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8);

    private int firstCardId = 0, secondCardId = 0;
    private int firstClickedCardId = 0, secondClickedCardId = 0;
    private int remaining = 16, moves = 0;
    private int addedUserScoreId, addedGlobalScoreId;
    private String pictureSet;
    private List<UserHighScore> bestUserScores = new ArrayList<>();
    private List<GlobalHighScore> bestGlobalScores = new ArrayList<>();
    private Boolean soundEnabled = true, addedUserScore = false, addedGlobalScore = false;
    private String userName = "";
    MediaPlayer clickSound, successSound, failSound;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    private double lat, lon;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(this);
        setContentView(R.layout.game);
        loadUserScore();
        try {
            loadGlobalScore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        pictureSet = sh.getString("pictureSet", "Animals");

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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }


    public void onButton(View card) throws InterruptedException, IOException {
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
                    endGame(card);
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

    public int setPicture(int clickedCardId) {
        int[] pictureArray;
        if(pictureSet == "Animals")
        {
            pictureArray = new int[]{R.drawable.antelope, R.drawable.antelope, R.drawable.chimpanzee, R.drawable.elephant, R.drawable.giraffe, R.drawable.hippopotamus, R.drawable.lion, R.drawable.rhino, R.drawable.zebra};
        }
        else
        {
            pictureArray = new int[]{ R.drawable.bmw, R.drawable.bmw, R.drawable.fiat, R.drawable.mercedes, R.drawable.nissan, R.drawable.peugeot, R.drawable.seat, R.drawable.skoda, R.drawable.volkswagen};
        }
        IVArray[clickedCardId - 1].setImageResource(pictureArray[values.get(clickedCardId - 1)]);
        return values.get(clickedCardId - 1);
    }

    public void endGame(View view) throws IOException {
        compareUserScore();
        compareGlobalScore();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You win");
        final EditText input = new EditText(this);
        builder.setView(input);
        String message = "";
        if(addedUserScore && addedGlobalScore)
        {
            message = "Congratulations, you win!\nNumber of moves: " + moves + "\nYour result is good enough to be added to both your personal scores and to the global scores! You may check them out in the Score section of the main menu.\nPlease enter your name:";
        }
        else if(addedUserScore && !addedGlobalScore)
        {
            message = "Congratulations, you win!\nNumber of moves: " + moves + "\nYour result is good enough to be added to both your personal scores! You may check them out in the Score section of the main menu\nPlease enter your name:";
        }
        else if(!addedUserScore && addedGlobalScore)
        {
            message = "Congratulations, you win!\nNumber of moves: " + moves + "\nYour result is good enough to be added to the global scores! You may check them out in the Score section of the main menu\nPlease enter your name:";
        }
        else
        {
            message = "Congratulations, you win!\nNumber of moves: " + moves + "\nPlease enter your name:";
        }
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        userName = input.getText().toString();
                        if(addedUserScore)
                        {
                            UserHighScore userHighScore = bestUserScores.get(addedUserScoreId);
                            userHighScore.setName(userName);
                        }
                        if(addedGlobalScore)
                        {
                            GlobalHighScore globalHighScore = bestGlobalScores.get(addedGlobalScoreId);
                            globalHighScore.setName(userName);
                        }
                        if(addedUserScore)
                        {
                            saveUserScore();
                        }
                        if(addedGlobalScore)
                        {
                            saveGlobalScore();
                        }
                        finish();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public void loadUserScore()
    {
       bestUserScores = db.getAllScores();
    }

    public void compareUserScore()
    {
        for(int i = 0; i < 5; i++)
        {
            if(bestUserScores.size() == i || moves < bestUserScores.get(i).getMoves())
            {
                addedUserScoreId = i;
                addedUserScore = true;
                UserHighScore userHighScore = new UserHighScore((i + 1), "tempName", moves);
                bestUserScores.add(i, userHighScore);
                break;
            }
        }
        if(bestUserScores.size() == 6)
        {
            bestUserScores.remove(5);
        }
    }

    public void saveUserScore()
    {
        db.refreshTable();
        for(int i = 0; i < bestUserScores.size(); i++)
        {
            UserHighScore userHighScore = bestUserScores.get(i);
            db.addScore(userHighScore);
        }
    }


    public void loadGlobalScore() throws IOException {
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
    }

    public void compareGlobalScore() throws IOException {
        for(int i = 0; i < 5; i++)
        {
            if(bestGlobalScores.size() == i || moves < bestGlobalScores.get(i).getMoves())
            {
                addedGlobalScoreId = i;
                addedGlobalScore = true;
                GlobalHighScore globalHighScore = new GlobalHighScore(moves, "tempName", getCountry());
                bestGlobalScores.add(i, globalHighScore);
                break;
            }
        }
        if(bestGlobalScores.size() == 6)
        {
            bestGlobalScores.remove(5);
        }
        if(addedGlobalScore)
        {
       //     saveGlobalScore();
        }
    }

    public void saveGlobalScore()
    {
        String moveAmounts = "";
        for(int i = 0; i < bestGlobalScores.size(); i++)
        {
            moveAmounts = moveAmounts + String.valueOf(bestGlobalScores.get(i).getMoves()) + ";" + bestGlobalScores.get(i).getName() + ";" +bestGlobalScores.get(i).getCountry() + "\n";
        }

        webView = findViewById(R.id.webView);
        String setResultsUrl = "http://tamztest.8u.cz/addNewResults.php";
        String postData = null;
        try {
            postData = "moves=" + URLEncoder.encode(moveAmounts, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        webView.postUrl(setResultsUrl,postData.getBytes());
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


    @SuppressLint("MissingPermission")
    private void getLastLocation()
    {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task)
                    {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        }
                        else {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            try {
                                getCountry();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

            else {
                Toast.makeText(this, "Please turn on your location.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData()
    {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult)
        {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
        }
    };

    private String getCountry() throws IOException {
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lat, lon, 1);
        String countryName = "";
        if (addresses.size() > 0)
        {
            countryName=addresses.get(0).getCountryName();
        }
        if(countryName == "" || countryName == null)
        {
            countryName = "Unknown";
        }
        return countryName;
    }

    private boolean checkPermissions()
    {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ID);
    }

    private boolean isLocationEnabled()
    {
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}
