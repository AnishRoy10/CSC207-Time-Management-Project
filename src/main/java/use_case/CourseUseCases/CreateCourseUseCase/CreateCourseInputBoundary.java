package use_case.CourseUseCases.CreateCourseUseCase;

public interface CreateCourseInputBoundary {
    /**
     * Execute the use case with the given input data.
     * @param inputData Input data for the use case.
     */
    void execute(CreateCourseInputData inputData);
}
