package use_case.TimerUseCases.SetTimerUseCase;

import entity.Timer;
import entity.User;

/**
 * Class representing the use case interactor for the set timer use case.
 */
public class SetTimerInteractor implements SetTimerInputBoundary {
    final SetTimerDataAccessInterface userDataAccessObject;
    final SetTimerOutputBoundary userPresenter;

    /**
     * Constructor for SetTimerInteractor
     * @param setTimerDataAccessInterface data access interface allowing the interactor to access the data access object
     * @param setTimerOutputBoundary output boundary allowing the interactor to access the presenter
     */
    public SetTimerInteractor(SetTimerDataAccessInterface setTimerDataAccessInterface,
                              SetTimerOutputBoundary setTimerOutputBoundary) {
        this.userDataAccessObject = setTimerDataAccessInterface;
        this.userPresenter = setTimerOutputBoundary;
    }

    /**
     * Executes the set timer use case. Creates a new timer entity to be saved to the user entity.
     * @param setTimerInputData input data for set timer use case
     */
    public void execute(SetTimerInputData setTimerInputData) {
        if (setTimerInputData.getHours() == 0 &&
                setTimerInputData.getMinutes() == 0 &&
                setTimerInputData.getSeconds() == 0) {
            userPresenter.prepareFailView("Invalid Input");
        }
        else {
            Timer timer = new Timer(setTimerInputData.getHours(),
                    setTimerInputData.getMinutes(),
                    setTimerInputData.getSeconds());

            User user = userDataAccessObject.load();
            user.addTimer(timer);
            userDataAccessObject.save(user);

            long timerLength = timer.timerLength();
            int hours = (int) (timerLength / 3600000);
            int minutes = (int) ((timerLength - hours * 3600000) / 60000);
            int seconds = (int) ((timerLength - hours * 3600000 - minutes * 60000) / 1000);

            SetTimerOutputData setTimerOutputData = new SetTimerOutputData(hours, minutes, seconds);
            userPresenter.prepareSuccessView(setTimerOutputData);
        }
    }
}
