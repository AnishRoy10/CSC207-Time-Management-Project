package interface_adapter.presenter;

import interface_adapter.viewmodel.CourseListViewModel;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesOutputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesOutputData;

public class CourseListPresenter implements LoadCoursesOutputBoundary {
    private final CourseListViewModel viewModel;

    public CourseListPresenter(CourseListViewModel viewModel) {
        this.viewModel = viewModel;
    }


    @Override
    public void present(LoadCoursesOutputData outputData) {
        if (outputData.isSuccess()) {
            viewModel.setSuccess(outputData.isSuccess());
            viewModel.setMessage(outputData.getMessage());
            viewModel.setCourses(outputData.getCourses());
        } else {
            viewModel.setSuccess(outputData.isSuccess());
            viewModel.setMessage(outputData.getMessage());
        }
    }
}
