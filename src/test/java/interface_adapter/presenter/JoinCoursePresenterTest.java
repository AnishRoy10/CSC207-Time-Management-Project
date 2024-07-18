package interface_adapter.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import interface_adapter.JoinCourseUseCase.JoinCoursePresenter;
import interface_adapter.JoinCourseUseCase.JoinCourseViewModel;
import use_case.JoinCourseUseCase.JoinCourseResponseModel;

public class JoinCoursePresenterTest {
    private JoinCoursePresenter presenter;
    private JoinCourseViewModel viewModel;

    @BeforeEach
    public void setup() {
        viewModel = new JoinCourseViewModel();
        presenter = new JoinCoursePresenter(viewModel);
    }

    @Test
    public void testSuccessfulJoin() {
        JoinCourseResponseModel responseModel = new JoinCourseResponseModel(true, "Success");

        presenter.present(responseModel);

        assertTrue(viewModel.getResponse());
        assertEquals("Success", viewModel.getMessage());
    }

    @Test
    public void testBadInput() {
        JoinCourseResponseModel responseModel = new JoinCourseResponseModel(false, "Bad");

        presenter.present(responseModel);

        assertFalse(viewModel.getResponse());
        assertEquals("Bad", viewModel.getMessage());
    }
}
