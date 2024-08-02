package interface_adapter.presenter;

import interface_adapter.viewmodel.CourseViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseOutputData;

public class CourseViewPresenterTest {
    private CourseViewPresenter presenter;
    private CourseViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = new CourseViewModel();
        presenter = new CourseViewPresenter(viewModel);
    }

    @Test
    public void viewNonexistentCourse() {
        ViewCourseOutputData outputData = new ViewCourseOutputData(
                false,
                "That course does not exist."
        );

        presenter.present(outputData);

        Assertions.assertFalse(viewModel.isSuccess());
        Assertions.assertEquals("That course does not exist.", viewModel.getMessage());
    }

    @Test
    public void viewValidCourse() {
        ViewCourseOutputData outputData = new ViewCourseOutputData(
                true,
                ""
        );

        presenter.present(outputData);

        Assertions.assertTrue(viewModel.isSuccess());
        Assertions.assertEquals("", viewModel.getMessage());
    }
}
