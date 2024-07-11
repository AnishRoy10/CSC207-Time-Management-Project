package use_case.join_course;

public interface JoinCourseOutputBoundary {
    
    void prepareSuccessView(JoinCourseOutputData status);

    void prepareFailureView(String error);
}
