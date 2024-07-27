package app.gui;

import data_access.FileCacheUserDataAccessObject;
import data_access.InMemoryTimerDataAccessObject;
import data_access.TimerDataAccessObject;
import interface_adapter.presenter.TimerPresenter;
import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.controller.TimerController;
import framework.view.SetTimerView;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInteractor;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInteractor;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerInteractor;

import java.io.IOException;

public class TimerInitializer {

    public static void main(String[] args) {
        try {
            SetTimerViewModel setTimerViewModel = new SetTimerViewModel("set timer");
            RunningTimerViewModel runningTimerViewModel = new RunningTimerViewModel("running timer");

//            InMemoryTimerDataAccessObject dataAccessObject = new InMemoryTimerDataAccessObject();

            String activeDir = System.getProperty("user.dir");
            String filePath = (activeDir + "\\src\\main\\java\\data_access\\userCache.json");
            FileCacheUserDataAccessObject fileCacheUserDataAccessObject = new FileCacheUserDataAccessObject(filePath);
            TimerDataAccessObject dataAccessObject = new TimerDataAccessObject(fileCacheUserDataAccessObject);

            TimerPresenter presenter = new TimerPresenter(setTimerViewModel, runningTimerViewModel);
            SetTimerInteractor setTimerInteractor = new SetTimerInteractor(dataAccessObject, presenter);
            UpdateTimerInteractor updateTimerInteractor = new UpdateTimerInteractor(dataAccessObject, presenter);
            PauseTimerInteractor pauseTimerInteractor = new PauseTimerInteractor(dataAccessObject, presenter);
            TimerController controller = new TimerController(setTimerInteractor, updateTimerInteractor,
                    pauseTimerInteractor);

            SetTimerView view = new SetTimerView(controller, setTimerViewModel, runningTimerViewModel);
            view.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing Timer");
        }

    }
}
