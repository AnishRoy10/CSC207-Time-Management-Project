package use_case.CourseUseCases.JoinCourseUseCase;

public class JoinCourseOutputData {
    
    final private boolean response;
    final private String message;

    public JoinCourseOutputData(boolean response, String message) {
        this.response = response;
        this.message = message;
    }

    public boolean getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }
}
