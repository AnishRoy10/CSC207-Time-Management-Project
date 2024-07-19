package data_access;

import entity.Timer;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerDataAccessInterface;

public class InMemoryTimerDataAccessObject implements SetTimerDataAccessInterface {
    private Timer timer;

    @Override
    public void save(Timer timer) {
        this.timer = timer;
    }
}
