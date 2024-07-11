package interface_adapter.setTimer;

import use_case.timer.SetTimerOutputBoundary;
import use_case.timer.SetTimerOutputData;

public class SetTimerPresenter implements SetTimerOutputBoundary {

    private final SetTimerViewModel setTimerViewModel;

    public SetTimerPresenter(SetTimerViewModel setTimerViewModel) {
        this.setTimerViewModel = setTimerViewModel;

    }

    @Override
    public void prepareSuccessView(SetTimerOutputData response) {

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
