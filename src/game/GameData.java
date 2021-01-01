package game;

import java.io.Serializable;

public class GameData implements Serializable {

	private int score;
    private String name;
    private String date;
    private int turn;
	private int[] scores;

    public GameData()
    {
       
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

	public void setScores(int[] scores) {
		this.scores = scores;
		
	}

    
}
