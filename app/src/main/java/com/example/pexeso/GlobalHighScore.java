package com.example.pexeso;

public class GlobalHighScore {
    private int moves;
    private String country;

    public GlobalHighScore(int moves, String country)
    {
        this.moves = moves;
        this.country = country;
    }

    public int getMoves()
    {
        return moves;
    }

    public String getCountry()
    {
        return country;
    }

    @Override
    public String toString()
    {
        return "Moves: " + moves + ", country: " + country;
    }
}
