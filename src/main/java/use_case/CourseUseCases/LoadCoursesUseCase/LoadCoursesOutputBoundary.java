package use_case.CourseUseCases.LoadCoursesUseCase;

public interface LoadCoursesOutputBoundary {
    /**
     * Present the list of courses to the user.
     * @param outputData The output data to present.
     */
    void present(LoadCoursesOutputData outputData);
}
