package use_case.TimerUseCases.SetTimerUseCase;

import entity.User;

public interface SetTimerDataAccessInterface {
    void save(User user);
    User load();
}
