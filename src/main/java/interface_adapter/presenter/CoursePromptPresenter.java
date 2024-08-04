package interface_adapter.presenter;

import interface_adapter.viewmodel.CoursePromptViewModel;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseOutputBoundary;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseOutputData;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseOutputBoundary;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseOutputData;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseOutputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseOutputData;

/**
 * Presenter for all prompt-based course use cases.
 */
public class CoursePromptPresenter implements CreateCourseOutputBoundary, JoinCourseOutputBoundary, LeaveCourseOutputBoundary {
    /// The view model for the create course use case.
    private final CoursePromptViewModel createCourseViewModel;

    /// The view model for the join course use case.
    private final CoursePromptViewModel joinCourseViewModel;

    /// The view model for the leave course use case.
    private final CoursePromptViewModel leaveCourseViewModel;

    /**
     * Construct a new presenter instance.
     * @param createCourseViewModel View model for the create course use case.
     * @param joinCourseViewModel   View model for the join course use case.
     * @param leaveCourseViewModel  View model for the leave course use case.
     */
    public CoursePromptPresenter(CoursePromptViewModel createCourseViewModel,
                                 CoursePromptViewModel joinCourseViewModel,
                                 CoursePromptViewModel leaveCourseViewModel) {
        this.createCourseViewModel = createCourseViewModel;
        this.joinCourseViewModel = joinCourseViewModel;
        this.leaveCourseViewModel = leaveCourseViewModel;
    }

    /**
     * Present data for the create course use case.
     * @param outputData The data to present.
     */
    @Override
    public void present(CreateCourseOutputData outputData) {
        this.createCourseViewModel.setResponse(outputData.getResponse());
        this.createCourseViewModel.setMessage(outputData.getMessage());
    }

    /**
     * Present data for the join course use case.
     * @param outputData The data to present.
     */
    @Override
    public void present(JoinCourseOutputData outputData) {
        this.joinCourseViewModel.setResponse(outputData.getResponse());
        this.joinCourseViewModel.setMessage(outputData.getMessage());
    }

    /**
     * Present data for the leave course use case.
     * @param outputData The data to present.
     */
    @Override
    public void present(LeaveCourseOutputData outputData) {
        this.leaveCourseViewModel.setResponse(outputData.getResponse());
        this.leaveCourseViewModel.setMessage(outputData.getMessage());
    }
}
