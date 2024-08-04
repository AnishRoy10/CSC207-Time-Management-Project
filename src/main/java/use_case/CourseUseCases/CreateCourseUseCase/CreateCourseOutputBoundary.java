package use_case.CourseUseCases.CreateCourseUseCase;

public interface CreateCourseOutputBoundary {
    /**
     * Present information to the create course prompt view.
     * @param outputData Output data to present.
     */
    void present(CreateCourseOutputData outputData);
}
