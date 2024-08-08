package use_case.CourseUseCases.LeaveCourseUseCase;

import entity.Course;
import entity.User;
import repositories.CourseRepository;
import repositories.UserRepository;

import java.io.IOException;

/**
 * Interactor for the leave course use case.
 */
public class LeaveCourseUseCase implements LeaveCourseInputBoundary {
    /// The presenter for this use case.
    private final LeaveCourseOutputBoundary presenter;

    /// The data access object for users.
    private final UserRepository userDataAccessObject;

    /// The data access object for courses.
    private final CourseRepository courseDataAccessObject;

    /**
     * Construct a new interactor instance.
     * @param presenter              Presenter for this interactor.
     * @param userDataAccessObject   User data access object for this interactor.
     * @param courseDataAccessObject Course data access object for this interactor.
     */
    public LeaveCourseUseCase(LeaveCourseOutputBoundary presenter,
                              UserRepository userDataAccessObject,
                              CourseRepository courseDataAccessObject) {
        this.presenter = presenter;
        this.userDataAccessObject = userDataAccessObject;
        this.courseDataAccessObject = courseDataAccessObject;
    }

    /**
     * Execute this use case.
     * @param inputData Input data to use.
     */
    @Override
    public void execute(LeaveCourseInputData inputData) {
        try {
            String courseName = inputData.getCourseName();
            String username = inputData.getUsername();
            Course course = courseDataAccessObject.findByName(courseName);

            if (course == null) {
                LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                        false,
                        courseName + " is not a course."
                );
                presenter.present(outputData);
                return;
            }

            if (!course.containsUser(username)) {
                LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                        false,
                        "You are not in " + courseName + "."
                );
                presenter.present(outputData);
                return;
            }

            User user = userDataAccessObject.ReadFromCache(username);
            if (user == null) {
                LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                        false,
                        "User not found."
                );
                presenter.present(outputData);
                return;
            }

            System.out.println("Before Removal: " + course.getUserNames().length + " users in course.");

            if (user.removeCourse(courseName) && course.removeUser(username)) {
                userDataAccessObject.WriteToCache(user);
                courseDataAccessObject.WriteToCache(course);

                System.out.println("After Removal: " + course.getUserNames().length + " users in course.");

                LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                        true,
                        "You have been removed from " + courseName + "."
                );
                presenter.present(outputData);
                return;
            }

            LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                    false,
                    "Something went wrong removing you from " + courseName + "."
            );
            presenter.present(outputData);
        } catch (IOException e) {
            LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                    false,
                    "Something went wrong."
            );
            presenter.present(outputData);
        }
    }
}
