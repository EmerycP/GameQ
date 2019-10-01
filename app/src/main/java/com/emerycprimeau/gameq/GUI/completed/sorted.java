package com.emerycprimeau.gameq.GUI.completed;

import com.emerycprimeau.gameq.models.gameCompleted;
import com.emerycprimeau.gameq.models.transfer.gameCompletedResponse;

import java.util.Comparator;

public class sorted implements Comparator<gameCompletedResponse> {
    public int compare(gameCompletedResponse a, gameCompletedResponse b)
    {
        return b.score - a.score;
    }
}
