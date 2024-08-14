package use_case.TimerUseCases.UpdateTimerUseCase;

/**
 * Input boundary between the controller and use case interactor.
 */
public interface UpdateTimerInputBoundary {
    void execute(UpdateTimerInputData updateTimerInputData);
}
