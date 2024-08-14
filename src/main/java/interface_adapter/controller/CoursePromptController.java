package interface_adapter.controller;

import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseInputBoundary;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseInputData;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseInputBoundary;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseInputData;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputData;

/**
 * Controller for all prompt-based course use cases.
 */
public class CoursePromptController {
    /// The interactor for the create course use case.
    private CreateCourseInputBoundary createCourseInteractor;

    // The interactor for the join course use case.
    private JoinCourseInputBoundary joinCourseInteractor;

    // The interactor for the leave course use case.
    private LeaveCourseInputBoundary leaveCourseInteractor;

    /**
     * Construct a controller instance just for the create course use case.
     * @param createCourseInteractor Interactor for the create course use case.
     */
    public CoursePromptController(CreateCourseInputBoundary createCourseInteractor) {
        this.createCourseInteractor = createCourseInteractor;
    }

    /**
     * Construct a controller instance just for the join course use case.
     * @param joinCourseInteractor Interactor for the join course use case.
     */
    public CoursePromptController(JoinCourseInputBoundary joinCourseInteractor) {
        this.joinCourseInteractor = joinCourseInteractor;
    }

    /**
     * Construct a controller instance just for the leave course use case.
     * @param leaveCourseInteractor Interactor for the leave course use case.
     */
    public CoursePromptController(LeaveCourseInputBoundary leaveCourseInteractor) {
        this.leaveCourseInteractor = leaveCourseInteractor;
    }

    /**
     * Execute the create course use case.
     * @param courseName        The name of the course to create.
     * @param courseDescription The description of the course to create.
     */
    public void createCourse(String username, String courseName, String courseDescription) {
        createCourseInteractor.execute(new CreateCourseInputData(username, courseName, courseDescription));
    }

    /**
     * Execute the join course use case.
     * @param username   The username of the current user.
     * @param courseName The name of the course to join.
     */
    public void joinCourse(String username, String courseName) {
        joinCourseInteractor.execute(new JoinCourseInputData(username, courseName));
    }

    /**
     * Execute the leave course use case.
     * @param username   The username of the current user.
     * @param courseName The name of the course to leave.
     */
    public void leaveCourse(String username, String courseName) {
        leaveCourseInteractor.execute(new LeaveCourseInputData(username, courseName));
    }
}
