package use_case.timer;

public interface SetTimerOutputBoundary {
    void prepareSuccessView();
    void prepareFailView(String errorMessage);
}
