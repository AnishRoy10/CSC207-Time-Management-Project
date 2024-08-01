package use_case.CourseUseCases.LoadCoursesUseCase;

public interface LoadCoursesInputBoundary {
    /**
     * Execute the load courses use case with the given input data.
     * @param inputData The input data to use.
     */
    void execute (LoadCoursesInputData inputData);
}
