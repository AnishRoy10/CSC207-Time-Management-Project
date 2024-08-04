package use_case.CourseUseCases.JoinCourseUseCase;

public interface JoinCourseInputBoundary {
    /**
     * Execute this use case with the provided input data.
     * @param inputData The input data to use.
     */
    void execute(JoinCourseInputData inputData);
}
