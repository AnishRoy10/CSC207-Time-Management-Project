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
    public void addScore(User user, int score) {
        scores.put(user.username, score);
    }

    // Removes a score from the leaderboard
    public void removeScore(User user) {
        scores.remove(user.username);
    }

    // Updates a user score in the leaderboard
    public void updateScore(User user, int newScore) {
        int score = user.score;
        scores.put(user.username, score + newScore);
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

    // Checking if the tasks is completed to add scores, if completed adds a certain score.
    public void taskCompleted(User user, Task task) {
        if (task.isCompleted()) {
            updateScore(user, 500);
        }
    }

    //



}
