package use_case.TimerUseCases.PauseTimerUseCase;

/**
 * Output boundary between the use case interactor and presenter.
 */
public interface PauseTimerOutputBoundary {
    void prepareSuccessView(PauseTimerOutputData pauseTimerOutputData);
}
