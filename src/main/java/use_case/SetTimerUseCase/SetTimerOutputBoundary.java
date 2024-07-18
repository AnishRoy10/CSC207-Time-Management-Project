package use_case.SetTimerUseCase;

public interface SetTimerOutputBoundary {
    void prepareSuccessView(SetTimerOutputData timer);
    void prepareFailView(String errorMessage);
}
