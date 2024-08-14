package use_case.TimerUseCases.PauseTimerUseCase;

/**
 * Input boundary between the controller and use case interactor.
 */
public interface PauseTimerInputBoundary {
    void execute(PauseTimerInputData pauseTimerInputData);
}
