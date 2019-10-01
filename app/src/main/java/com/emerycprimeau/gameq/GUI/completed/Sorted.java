package com.emerycprimeau.gameq.GUI.completed;

import com.emerycprimeau.gameq.models.transfer.GameCompletedResponse;

import java.util.Comparator;

public class Sorted implements Comparator<GameCompletedResponse> {
    public int compare(GameCompletedResponse a, GameCompletedResponse b)
    {
        return b.score - a.score;
    }
}
