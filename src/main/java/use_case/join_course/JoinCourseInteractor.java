package use_case.join_course;

import entity.Course;
import entity.User;

public class JoinCourseInteractor implements JoinCourseInputBoundary {
    
    final private JoinCourseDataAccessInterface dataAccessInterface;
    final private JoinCourseOutputBoundary joinCoursePresenter;

    public JoinCourseInteractor(JoinCourseDataAccessInterface dataAccessInterface,
                                JoinCourseOutputBoundary joinCoursePresenter) {
        this.dataAccessInterface = dataAccessInterface;
        this.joinCoursePresenter = joinCoursePresenter;
    }

    @Override
    public void execute(JoinCourseInputData joinCourseInputData) {
        User user = joinCourseInputData.getUser();
        Course course = joinCourseInputData.getCourse();

        if (!dataAccessInterface.courseExists(course.getName())) {
            joinCoursePresenter.prepareFailureView("Course " + course.getName() + " does not exist.");
        } else {
            course.addUser(user);

            JoinCourseOutputData status = new JoinCourseOutputData(false);
            joinCoursePresenter.prepareSuccessView(status);

        }
    }
}
