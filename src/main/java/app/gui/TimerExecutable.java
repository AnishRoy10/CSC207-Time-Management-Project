package app.gui;

import data_access.InMemoryTimerDataAccessObject;
import interface_adapter.setTimer.SetTimerPresenter;
import interface_adapter.setTimer.SetTimerViewModel;
import interface_adapter.setTimer.SetTimerController;
import framework.view.SetTimerView;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInteractor;

public class TimerExecutable {
    public static void main(String[] args) {
        SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
        RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");

        InMemoryTimerDataAccessObject dataAccessObject = new InMemoryTimerDataAccessObject();

        SetTimerPresenter presenter = new SetTimerPresenter(setTimerViewModel, runningTimerViewModel);
        SetTimerInteractor interactor = new SetTimerInteractor(dataAccessObject, presenter);
        SetTimerController controller = new SetTimerController(interactor);

        SetTimerView view = new SetTimerView(controller, setTimerViewModel, runningTimerViewModel);
        view.setVisible(true);
    }
}
