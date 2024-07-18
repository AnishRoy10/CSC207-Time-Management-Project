package interface_adapter.JoinCourseUseCase;

import use_case.JoinCourseUseCase.JoinCourseOutputBoundary;
import use_case.JoinCourseUseCase.JoinCourseResponseModel;

public class JoinCoursePresenter implements JoinCourseOutputBoundary {
	final private JoinCourseViewModel viewModel;

	public JoinCoursePresenter(JoinCourseViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void present(JoinCourseResponseModel responseModel) {
		viewModel.setResponse(responseModel.getResponse());
		viewModel.setMessage(responseModel.getMessage());
	}
}
