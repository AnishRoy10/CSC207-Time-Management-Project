package use_case.CourseUseCases.CreateCourseUseCase;

import data_access.CourseDataAccessObject;
import entity.Course;
import repositories.CourseRepository;

import java.io.IOException;

/**
 * Interactor for the create course use case.
 */
public class CreateCourseUseCase implements CreateCourseInputBoundary {
    /// The presenter for this use case.
    private final CreateCourseOutputBoundary presenter;

    /// The data access object for courses.
    private final CourseRepository courseDataAccessObject;

    /**
     * Construct a new interactor instance.
     * @param presenter              The presenter to use.
     * @param courseDataAccessObject The course data access object to use.
     */
    public CreateCourseUseCase(CreateCourseOutputBoundary presenter, CourseDataAccessObject courseDataAccessObject) {
        this.presenter = presenter;
        this.courseDataAccessObject = courseDataAccessObject;
    }

    /**
     * Execute this use case.
     * @param inputData Input data to use.
     */
    @Override
    public void execute(CreateCourseInputData inputData) {
        String courseName = inputData.getCourseName();
        String courseDescription = inputData.getCourseDescription();

        /// check if a course by the name exists already
        if (courseDataAccessObject.courseExists(courseName)) {
            CreateCourseOutputData outputData = new CreateCourseOutputData(
                    false,
                    courseName + " already exists. Try joining it!"
            );
            presenter.present(outputData);
            return;
        }

        /// create the course and write changes
        Course course = new Course(courseName, courseDescription);
        courseDataAccessObject.WriteToCache(course);

        CreateCourseOutputData outputData = new CreateCourseOutputData(
                true,
                courseName + " has been created. Try joining it!"
        );
        presenter.present(outputData);
    }
}
