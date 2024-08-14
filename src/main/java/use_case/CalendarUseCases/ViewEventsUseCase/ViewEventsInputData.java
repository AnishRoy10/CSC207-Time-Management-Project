package use_case.CalendarUseCases.ViewEventsUseCase;

import java.time.LocalDateTime;

/**
 * Stores the start and end of the day on which events are to be viewed as
 * LocalDateTime objects
 */
public class ViewEventsInputData {
    private LocalDateTime start;
    private LocalDateTime end;
    public ViewEventsInputData(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
    // Getter method for start
    public LocalDateTime getStart(){return this.start;}

    // Getter method for end
    public LocalDateTime getEnd() {return this.end;}

}
