package use_case.TimerUseCases.SetTimerUseCase;

/**
 * Input boundary between the controller and use case interactor.
 */
public interface SetTimerInputBoundary {
    void execute(SetTimerInputData setTimerInputData);
}
