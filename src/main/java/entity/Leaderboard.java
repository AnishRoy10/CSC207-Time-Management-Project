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

    // A HashMap to store the user's scores

    protected Map<String, Integer> scores;

    public Leaderboard(String name) {
        this.name = name;
        this.scores = new HashMap<>() ;
    }

    /**
     * Adds a user score to the leaderboard.
     * @param user The user.
     * @param score The score to add.
     */
    public void addScore(User user, int score) {
        scores.put(user.getUsername(), score);
    }

    /**
     * Removes a user score from the leaderboard.
     * @param user The user.
     */
    public void removeScore(User user) {
        scores.remove(user.getUsername());
    }

    /**
     * Updates a user score in the leaderboard.
     * @param user The user.
     * @param newScore The new score to add to the current score.
     */
    public void updateScore(User user, int newScore) {
        int score = user.getScore();
        scores.put(user.getUsername(), score + newScore);
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
     * @param user The user.
     * @param task The task to check.
     */
    public void taskCompleted(User user, Task task) {
        if (task.isCompleted()) {
            updateScore(user, 500);
        }
    }



}
