package interface_adapter.controller;

import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputData;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputData;

public class CourseViewController {
    private final LoadCoursesInputBoundary loadCoursesInteractor;
    private final ViewCourseInputBoundary viewCourseInteractor;

    public CourseViewController(
            ViewCourseInputBoundary viewCourseInteractor,
            LoadCoursesInputBoundary loadCoursesInteractor) {
        this.viewCourseInteractor = viewCourseInteractor;
        this.loadCoursesInteractor = loadCoursesInteractor;
    }
    public void viewCourse(String courseName) {
        viewCourseInteractor.execute(new ViewCourseInputData(courseName));
    }

    public void loadCourses(String username) {
        loadCoursesInteractor.execute(new LoadCoursesInputData(username));
    }
}
