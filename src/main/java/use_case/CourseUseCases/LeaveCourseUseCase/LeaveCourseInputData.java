package use_case.CourseUseCases.LeaveCourseUseCase;

public class LeaveCourseInputData {
    private final String username;
    private final String courseName;

    public LeaveCourseInputData(String username, String courseName) {
        this.username = username;
        this.courseName = courseName;
    }

    public String getUsername() {
        return username;
    }

    public String getCourseName() {
        return courseName;
    }
}
