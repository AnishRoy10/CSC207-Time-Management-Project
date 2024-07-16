package interface_adapter.join_course;

import entity.Course;
import entity.User;
import use_case.join_course.JoinCourseInputBoundary;
import use_case.join_course.JoinCourseRequestModel;

public class JoinCourseController {
    
    final JoinCourseInputBoundary joinCourseUseCaseInteractor;

    public JoinCourseController(JoinCourseInputBoundary joinCourseUseCaseInteractor) {
        this.joinCourseUseCaseInteractor = joinCourseUseCaseInteractor;
    }

    void execute(User user, Course course) {
        JoinCourseRequestModel joinCourseInputData = new JoinCourseRequestModel(user, course);
        joinCourseUseCaseInteractor.execute(joinCourseInputData);
    }
}
