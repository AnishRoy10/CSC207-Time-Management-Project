package interface_adapter.setTimer;

import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerOutputBoundary;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerOutputData;

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
