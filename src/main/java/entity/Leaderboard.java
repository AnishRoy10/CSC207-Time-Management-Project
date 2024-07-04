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

    // Removes a score from the leaderboard
    public void removeScore(String username) {
        scores.remove(username);
    }

    // Updates a user score in the leaderboard
    public void updateScore(String username, int score) {
        scores.put(username, score + scores.get(score));
    }

    // Clearing all scores from the leaderboard
    public void clearScores() {
        scores.clear();
    }

    // An abstract method to be implemented by subclasses
    public abstract void displayLeaderboard();


    // A method to get the scores
    public Map<String, Integer> getScores() {
        return scores;
    }

    // Getter for the leaderboard's name
    public String getName() {
        return name;
    }
     // Setter for the leaderboard's name
    public void setName(String name) {
        this.name = name;
    }



}