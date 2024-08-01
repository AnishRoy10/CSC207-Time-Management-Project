package use_case.CourseUseCases.JoinCourseUseCase;

public interface JoinCourseOutputBoundary {
    /**
     * Present the data object to the join course prompt view.
     * @param outputData The output data to display.
     */
    void present(JoinCourseOutputData outputData);
}
