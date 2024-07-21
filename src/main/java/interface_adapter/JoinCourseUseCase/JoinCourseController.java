package interface_adapter.JoinCourseUseCase;

import use_case.JoinCourseUseCase.JoinCourseInputBoundary;
import use_case.JoinCourseUseCase.JoinCourseRequestModel;

public class JoinCourseController {
    
    final private JoinCourseInputBoundary inputBoundary;

    public JoinCourseController(JoinCourseInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(String courseName) {
        JoinCourseRequestModel inputData = new JoinCourseRequestModel(courseName);
        inputBoundary.execute(inputData);
    }
}
