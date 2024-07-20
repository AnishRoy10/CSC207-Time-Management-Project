package interface_adapter.presenter;

import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.SetTimerUseCase.SetTimerOutputBoundary;
import use_case.SetTimerUseCase.SetTimerOutputData;

/**
 * Presenter for the timer, implementing the output boundaries for setting the timer.
 */
public class SetTimerPresenter implements SetTimerOutputBoundary {

    private final SetTimerViewModel setTimerViewModel;
    private final RunningTimerViewModel runningTimerViewModel;

    public SetTimerPresenter(SetTimerViewModel setTimerViewModel,
                             RunningTimerViewModel runningTimerViewModel) {
        this.setTimerViewModel = setTimerViewModel;
        this.runningTimerViewModel = runningTimerViewModel;

    }

    @Override
    public void prepareSuccessView(SetTimerOutputData response) {
        RunningTimerViewModel.setHOURS(Integer.toString(response.getHours()));
        RunningTimerViewModel.setMINUTES(Integer.toString(response.getMinutes()));
        RunningTimerViewModel.setSECONDS(Integer.toString(response.getSeconds()));
        runningTimerViewModel.setMessage("Success");

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
