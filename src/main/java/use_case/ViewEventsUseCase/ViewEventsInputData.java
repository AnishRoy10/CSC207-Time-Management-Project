package use_case.ViewEventsUseCase;
import java.time.*;
import java.time.LocalDateTime;

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
