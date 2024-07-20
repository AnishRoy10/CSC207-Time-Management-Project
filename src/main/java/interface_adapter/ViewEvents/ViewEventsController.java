package interface_adapter.ViewEvents;
import use_case.ViewEventsUseCase.ViewEventsInputData;
import use_case.ViewEventsUseCase.ViewEventsUseCaseInteractor;
import java.time.*;
public class ViewEventsController {

    private ViewEventsUseCaseInteractor viewEventsUseCaseInteractor;

    public ViewEventsController(ViewEventsUseCaseInteractor viewEventsUseCaseInteractor){
        this.viewEventsUseCaseInteractor = viewEventsUseCaseInteractor;
    }

    public void execute(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                0, 0);
        LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                23, 59);
        ViewEventsInputData viewEventsInputData = new ViewEventsInputData(start, end);
        ViewEventsUseCaseInteractor.execute(viewEventsInputData);
    }



}