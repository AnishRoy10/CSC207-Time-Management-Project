package entity;

import java.util.HashMap;
import java.util.Map;

/**
 *  The Leaderboard class represents a leaderboard that users can view their scores
    and others scores.
 */

public abstract class Leaderboard {
    //Name of the leaderboard
    private String name;

    // A HashMap to store users scores

    protected Map<String, Integer> scores;

    public Leaderboard(String name) {
        this.name = name;
        this.scores = new HashMap<>() ;
    }

    /**
     * Adds a user score to the leaderboard.
     * @param username The username of the user.
     * @param score The score to add.
     */
    public void addScore(String username, int score) {
        scores.put(username, score);
    }

    /**
     * Removes a user score from the leaderboard.
     * @param username The username of the user.
     */
    public void removeScore(String username) {
        scores.remove(username);
    }

    /**
     * Updates a user score in the leaderboard.
     * @param username The username of the user.
     * @param newScore The new score to add to the current score.
     */
    public void updateScore(String username, int newScore) {
        scores.put(username, newScore);
    }

    /**
     * Clears all scores from the leaderboard.
     */    public void clearScores() {
        scores.clear();
    }

    /**
     * Displays the leaderboard.
     */
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

    /**
     * Checks if the tasks is completed to add scores. If completed, adds a certain score.
     * @param username The username of the user.
     * @param task The task to check.
     */
    public void taskCompleted(String username, Task task) {
        if (task.isCompleted()) {
            int score = scores.get(username);
            updateScore(username, 500 + score);
        }
    }

    //TODO: automatically arranges in ascending order.



}
