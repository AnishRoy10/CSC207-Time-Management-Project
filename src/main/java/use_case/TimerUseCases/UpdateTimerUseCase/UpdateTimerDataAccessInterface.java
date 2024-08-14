package use_case.TimerUseCases.UpdateTimerUseCase;

import entity.User;

/**
 * Interface with data access methods required to complete the update timer use case.
 */
public interface UpdateTimerDataAccessInterface {
    User load();
}
