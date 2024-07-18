package app.gui;

import data_access.InMemoryTimerDataAccessObject;
import interface_adapter.setTimer.SetTimerPresenter;
import interface_adapter.setTimer.SetTimerViewModel;
import interface_adapter.setTimer.SetTimerController;
import framework.view.SetTimerView;
import use_case.timer.SetTimerInteractor;

public class TimerExecutable {
    public static void main(String[] args) {
        SetTimerViewModel viewModel = new SetTimerViewModel("set timer");

        InMemoryTimerDataAccessObject dataAccessObject = new InMemoryTimerDataAccessObject();

        SetTimerPresenter presenter = new SetTimerPresenter(viewModel);
        SetTimerInteractor interactor = new SetTimerInteractor(dataAccessObject, presenter);
        SetTimerController controller = new SetTimerController(interactor);

        SetTimerView view = new SetTimerView(controller, viewModel);
        view.setVisible(true);
    }
}
