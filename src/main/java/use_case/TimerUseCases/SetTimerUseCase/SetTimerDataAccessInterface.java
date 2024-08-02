package use_case.TimerUseCases.SetTimerUseCase;

import entity.User;

/**
 * Interface with data access methods required to complete the set timer use case.
 */
public interface SetTimerDataAccessInterface {
    void save(User user);
    User load();
}
