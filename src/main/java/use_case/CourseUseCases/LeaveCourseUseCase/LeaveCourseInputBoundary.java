package use_case.CourseUseCases.LeaveCourseUseCase;

public interface LeaveCourseInputBoundary {
    /**
     * Execute this use case with the given data.
     * @param inputData The input data to use.
     */
    void execute(LeaveCourseInputData inputData);
}
