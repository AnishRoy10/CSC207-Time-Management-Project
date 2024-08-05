package use_case.TimerUseCases.UpdateTimerUseCase;

/**
 * Output boundary between the use case interactor and presenter.
 */
public interface UpdateTimerOutputBoundary {
    void prepareSuccessView(UpdateTimerOutputData outputData);
}
