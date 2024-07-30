package entity;

import java.util.*;

/**
 *  The Leaderboard class represents a leaderboard that users can view their scores
    and others scores.
 */

public abstract class Leaderboard {
    private String name; // name of the leaderboard
    protected Map<String, Integer> scores; // a HashMap to store users' scores
    protected List<Map.Entry<String, Integer>> sortedScores; // a List to store sorted scores

    public Leaderboard(String name) {
        this.name = name;
        this.scores = new HashMap<>();
        this.sortedScores = new ArrayList<>();
    }

    public abstract String getType();


    /**
     * Adds a user score to the leaderboard.
     * @param username The username of the user.
     * @param score The score to add.
     */
    public void addScore(String username, int score) {
        int currentScore = scores.getOrDefault(username, 0);
        scores.put(username, currentScore + score);
        sortScores();
    }

    /**
     * Removes a user score from the leaderboard.
     * @param username The username of the user.
     */
    public void removeScore(String username) {
        scores.remove(username);
        sortScores();
    }

    /**
     * Updates a user score in the leaderboard.
     * @param username The username of the user.
     * @param newScore The new score to add to the current score.
     */
    public void updateScore(String username, int newScore) {
        scores.put(username, newScore);
        sortScores();
    }

    /**
     * Clears all scores from the leaderboard.
     */    public void clearScores() {
        scores.clear();
        sortedScores.clear();

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
     * @param points The amount of points to change.
     */
    public void taskCompleted(String username, int points) {
        int currentScore = scores.getOrDefault(username, 0);
        scores.put(username, currentScore + points);
    }


    /**
     * Sorts the scores list to ensure it is sorted by scores in ascending order.
     */
    private void sortScores() {
        sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((e1, e2) -> {
            int scoreComparison = Integer.compare(e1.getValue(), e2.getValue());
            if (scoreComparison == 0) {
                return e1.getKey().compareTo(e2.getKey());
            }
            return scoreComparison;
        });
        // Debug print statement to verify sorting
        System.out.println("Sorted scores: " + sortedScores);
    }


    // A method to get the sorted scores
    public List<Map.Entry<String, Integer>> getSortedScores() {
        return sortedScores;
    }

}
