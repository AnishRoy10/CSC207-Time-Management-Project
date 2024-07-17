package interface_adapter.join_course;

import entity.Course;
import entity.User;
import use_case.join_course.JoinCourseInputBoundary;
import use_case.join_course.JoinCourseRequestModel;

public class JoinCourseController {
    
    final private JoinCourseInputBoundary inputBoundary;

    public JoinCourseController(JoinCourseInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(User user, String courseName) {
        JoinCourseRequestModel inputData = new JoinCourseRequestModel(user, courseName);
        inputBoundary.execute(inputData);
    }
}
