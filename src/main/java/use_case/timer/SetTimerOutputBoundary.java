package use_case.timer;

public interface SetTimerOutputBoundary {
    void prepareSuccessView(SetTimerOutputData timer);
    void prepareFailView(String errorMessage);
}
