package use_case.CourseUseCases.LeaveCourseUseCase;

public class LeaveCourseOutputData {
    private final boolean response;
    private final String message;

    public LeaveCourseOutputData(boolean response, String message) {
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
