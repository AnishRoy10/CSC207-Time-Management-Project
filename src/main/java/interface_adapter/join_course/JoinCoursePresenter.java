package interface_adapter.join_course;

import use_case.join_course.JoinCourseOutputBoundary;
import use_case.join_course.JoinCourseOutputData;

public class JoinCoursePresenter implements JoinCourseOutputBoundary {

    @Override
    public void prepareSuccessView(JoinCourseOutputData status) {
        throw new UnsupportedOperationException("Unimplemented method 'prepareSuccessView'");
    }

    @Override
    public void prepareFailureView(String error) {
        throw new UnsupportedOperationException("Unimplemented method 'prepareFailureView'");
    }
    
}
