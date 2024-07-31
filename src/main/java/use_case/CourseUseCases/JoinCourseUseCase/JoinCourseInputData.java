package use_case.CourseUseCases.JoinCourseUseCase;

public class JoinCourseInputData {
    private final String username;
    private final String courseName;

    public JoinCourseInputData(String username, String courseName) {
        this.username = username;
        this.courseName = courseName;
    }

    public String getUsername() {
        return username;
    }

    String getCourseName() {
        return courseName;
    }
}
