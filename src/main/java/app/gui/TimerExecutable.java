package app.gui;

import data_access.InMemoryTimerDataAccessObject;
import interface_adapter.presenter.SetTimerPresenter;
import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.controller.SetTimerController;
import framework.view.SetTimerView;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.SetTimerUseCase.SetTimerInteractor;

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
