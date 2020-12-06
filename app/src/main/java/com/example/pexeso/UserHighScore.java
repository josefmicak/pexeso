package com.example.pexeso;

public class UserHighScore {
    private int id;
    private String name;
    private int moves;

    public UserHighScore(int id, String name, int moves)
    {
        this.id = id;
        this.name = name;
        this.moves = moves;
    }

    public int getId()
    {
        return id;
    }

    public int getMoves()
    {
        return moves;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return id + ". Moves: " + moves + ", name: " + name;
    }
}
