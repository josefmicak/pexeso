package com.example.pexeso;

public class GlobalHighScore {
    private int moves;
    private String name;
    private String country;

    public GlobalHighScore(int moves, String name, String country)
    {
        this.moves = moves;
        this.name = name;
        this.country = country;
    }

    public int getMoves()
    {
        return moves;
    }

    public String getName() { return name; }

    public String getCountry()
    {
        return country;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Moves: " + moves + ", name: " + name + ", country: " + country;
    }
}
