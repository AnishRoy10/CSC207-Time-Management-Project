package use_case.TimerUseCases.SetTimerUseCase;

import entity.Timer;

public interface SetTimerDataAccessInterface {
    void save(Timer timer);
}
