package use_case.CourseUseCases.CreateCourseUseCase;

import data_access.CourseDataAccessObject;
import entity.Course;
import entity.User;
import repositories.CourseRepository;
import repositories.UserRepository;

import java.io.IOException;

/**
 * Interactor for the create course use case.
 */
public class CreateCourseUseCase implements CreateCourseInputBoundary {
    /// The presenter for this use case.
    private final CreateCourseOutputBoundary presenter;

    /// The data access object for courses.
    private final CourseRepository courseDataAccessObject;

    /// The data access object for users.
    private final UserRepository userDataAccessObject;

    /**
     * Construct a new interactor instance.
     * @param presenter              The presenter to use.
     * @param courseDataAccessObject The course data access object to use.
     */
    public CreateCourseUseCase(CreateCourseOutputBoundary presenter,
                               CourseRepository courseDataAccessObject,
                               UserRepository userDataAccessObject) {
        this.presenter = presenter;
        this.courseDataAccessObject = courseDataAccessObject;
        this.userDataAccessObject = userDataAccessObject;
    }

    /**
     * Execute this use case.
     * @param inputData Input data to use.
     */
    @Override
    public void execute(CreateCourseInputData inputData) {
        try {
            String username = inputData.getUsername();
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
            User user = userDataAccessObject.ReadFromCache(username);

            if (user == null) {
                CreateCourseOutputData outputData = new CreateCourseOutputData(
                        false,
                        "Something went wrong getting the current user."
                );
                presenter.present(outputData);
                return;
            }

            /// add the creation user to the new course
            course.addUser(user);
            user.addCourse(course);

            /// write changes
            courseDataAccessObject.WriteToCache(course);
            userDataAccessObject.WriteToCache(user);

            /// finally, present success
            CreateCourseOutputData outputData = new CreateCourseOutputData(
                    true,
                    courseName + " has been created."
            );
            presenter.present(outputData);
        } catch (IOException e) {
            CreateCourseOutputData outputData = new CreateCourseOutputData(
                    false,
                    "Something went wrong creating a course."
            );
            presenter.present(outputData);
        }

    }
}
