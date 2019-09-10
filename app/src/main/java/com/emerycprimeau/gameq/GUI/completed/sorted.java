package com.emerycprimeau.gameq.GUI.completed;

import com.emerycprimeau.gameq.models.gameCompleted;

import java.util.Comparator;

public class sorted implements Comparator<gameCompleted> {
    public int compare(gameCompleted a, gameCompleted b)
    {
        return b.score - a.score;
    }
}
