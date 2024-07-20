package use_case.TimerUseCases.SetTimerUseCase;

public interface SetTimerOutputBoundary {
    void prepareSuccessView(SetTimerOutputData timer);
    void prepareFailView(String errorMessage);
}
