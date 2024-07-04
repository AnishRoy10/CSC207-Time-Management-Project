package entity;

import java.util.HashMap;
import java.util.Map;

/* The Leaderboard class represents a leaderboard that users can view their scores
    and others scores.
*/

public abstract class Leaderboard {
    //Name of the leaderboard
    private String name;

    // A HashMap to store the user's scores

    protected Map<String, Integer> scores;

    public Leaderboard(String name) {
        this.name = name;
        this.scores = new HashMap<>() ;
    }

    // A method to add a user score
    public void addScore(String username, int score) {
        scores.put(username, score);
    }

    // Updates a user score in the leaderboard
    public void updateScore(String username, int score) {
        scores.put(username, score + scores.get(score));
    }

    // An abstract method to be implemented by subclasses
    public abstract void displayLeaderboard();


    // A method to get the scores
    public Map<String, Integer> getScores() {
        return scores;
    }

    // A method to get the leaderboard's name
    public String getName() {
        return name;
    }



}