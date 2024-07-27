package use_case.TimerUseCases.PauseTimerUseCase;

import entity.User;

public interface PauseTimerDataAccessInterface {
    User load();
    void save(User user);
}
