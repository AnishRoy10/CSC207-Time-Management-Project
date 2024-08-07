package use_case.CourseUseCases.ViewCourseUseCase;

public interface ViewCourseInputBoundary {
    /**
     * Execute the view course use case.
     * @param inputData Data about the course to display.
     */
    void execute(ViewCourseInputData inputData);
}
