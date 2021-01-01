package game;

import java.io.Serializable;

public class Player implements Serializable {
	

    private String name;
    private int[] scores;

    public Player(String name) {
        this.name = name;
        this.scores = new int[13];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = -1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }
}
