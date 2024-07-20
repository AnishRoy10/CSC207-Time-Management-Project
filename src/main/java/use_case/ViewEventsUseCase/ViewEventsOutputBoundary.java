package use_case.ViewEventsUseCase;

import java.io.IOException;

public interface ViewEventsOutputBoundary {
    void prepareEventView(ViewEventsOutputData viewEventsOutputData) throws IOException, ClassNotFoundException;
}
