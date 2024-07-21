package entity;

/**
 * Represents an all-time leaderboard.
 */

public class AllTimeLeaderboard extends Leaderboard {

    public AllTimeLeaderboard(String name) {
        super(name);
    }

    @Override
    public void displayLeaderboard() {
        System.out.println("All Time Leaderboard");
    }
}
