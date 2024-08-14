package use_case.CourseUseCases.LeaveCourseUseCase;

public interface LeaveCourseOutputBoundary {
    /**
     * Present the given output data to the user.
     * @param outputData The output data to use.
     */
    void present(LeaveCourseOutputData outputData);
}
