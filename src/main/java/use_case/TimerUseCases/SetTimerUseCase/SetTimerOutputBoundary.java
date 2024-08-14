package use_case.TimerUseCases.SetTimerUseCase;

/**
 * Output boundary between the use case interactor and presenter.
 */
public interface SetTimerOutputBoundary {
    void prepareSuccessView(SetTimerOutputData timer);
    void prepareFailView(String errorMessage);
}
