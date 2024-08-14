package use_case.TimerUseCases.PauseTimerUseCase;

import entity.User;

/**
 * Interface with data access methods required to complete the pause timer use case.
 */
public interface PauseTimerDataAccessInterface {
    User load();
    void save(User user);
}
