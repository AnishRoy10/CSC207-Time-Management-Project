package interface_adapter.presenter;

import interface_adapter.viewmodel.CourseViewModel;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseOutputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseOutputData;

public class CourseViewPresenter implements ViewCourseOutputBoundary {
    private final CourseViewModel viewModel;

    public CourseViewPresenter(CourseViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewCourseOutputData outputData) {
        if (outputData.isSuccess()) {
            viewModel.setSuccess(outputData.isSuccess());
            viewModel.setMessage(outputData.getMessage());
            viewModel.setCourseDescription(outputData.getCourseDescription());
            viewModel.setUsernames(outputData.getUsernames());
            viewModel.setTodoList(outputData.getTodoList());
            viewModel.setDailyLeaderboard(outputData.getDailyLeaderboard());
            viewModel.setMonthlyLeaderboard(outputData.getMonthlyLeaderboard());
            viewModel.setAllTimeLeaderboard(outputData.getAllTimeLeaderboard());
        } else {
            viewModel.setSuccess(outputData.isSuccess());
            viewModel.setMessage(outputData.getMessage());
        }
    }
}
