package use_case.LeaderboardUseCases.register_user;

import entity.Leaderboard;
import entity.User;

import java.util.List;

public class RegisterUserUseCase {
    private final List<Leaderboard> leaderboards;

    public RegisterUserUseCase(List<Leaderboard> leaderboards) {
        this.leaderboards = leaderboards;
    }

    public void registerUserInLeaderboards(User user) {


    }
}
