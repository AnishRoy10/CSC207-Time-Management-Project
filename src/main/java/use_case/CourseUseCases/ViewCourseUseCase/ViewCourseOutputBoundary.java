package use_case.CourseUseCases.ViewCourseUseCase;

public interface ViewCourseOutputBoundary {
    /**
     * Present the outgoing data about the course.
     * @param outputData The data to present.
     */
    void present(ViewCourseOutputData outputData);
}
