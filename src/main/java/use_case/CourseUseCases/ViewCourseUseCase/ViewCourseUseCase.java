package use_case.CourseUseCases.ViewCourseUseCase;

import repositories.CourseRepository;

import java.io.IOException;

/**
 * Interactor class to the view course use case.
 */
public class ViewCourseUseCase implements ViewCourseInputBoundary {
    private final ViewCourseOutputBoundary presenter;
    private final CourseRepository courseDataAccessObject;

    public ViewCourseUseCase(ViewCourseOutputBoundary presenter, CourseRepository courseDataAccessObject) {
        this.presenter = presenter;
        this.courseDataAccessObject = courseDataAccessObject;
    }

    @Override
    public void execute(ViewCourseInputData inputData) {
        try {
            /// TODO: with interface, load the course object and take its stuff
            throw new IOException();
        } catch (IOException e) {
            ViewCourseOutputData outputData = new ViewCourseOutputData(
                    false,
                    "Something went wrong trying to visualize " + inputData.getCourseName() + "."
            );
            presenter.present(outputData);
        }
    }
}
