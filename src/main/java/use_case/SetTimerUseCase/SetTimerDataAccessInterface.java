package use_case.SetTimerUseCase;

import entity.Timer;

public interface SetTimerDataAccessInterface {
    void save(Timer timer);
}
