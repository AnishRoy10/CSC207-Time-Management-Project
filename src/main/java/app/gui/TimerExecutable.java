package app.gui;

import data_access.InMemoryTimerDataAccessObject;
import interface_adapter.presenter.TimerPresenter;
import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.controller.TimerController;
import framework.view.SetTimerView;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.SetTimerUseCase.SetTimerInteractor;

public class TimerExecutable {
    public static void main(String[] args) {
        SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
        RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");

        InMemoryTimerDataAccessObject dataAccessObject = new InMemoryTimerDataAccessObject();

        TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
        SetTimerInteractor setTimerInteractor = new SetTimerInteractor(dataAccessObject, presenter);
        TimerController controller = new TimerController(setTimerInteractor);

        SetTimerView view = new SetTimerView(controller, setTimerViewModel, runningTimerViewModel);
        view.setVisible(true);
    }
}
