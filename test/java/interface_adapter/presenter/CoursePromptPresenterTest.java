package interface_adapter.presenter;

import interface_adapter.viewmodel.CoursePromptViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseOutputData;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseOutputData;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseOutputData;

public class CoursePromptPresenterTest {
    private CoursePromptPresenter presenter;
    private CoursePromptViewModel createViewModel;
    private CoursePromptViewModel joinViewModel;
    private CoursePromptViewModel leaveViewModel;

    @BeforeEach
    public void setup() {
        createViewModel = new CoursePromptViewModel();
        joinViewModel = new CoursePromptViewModel();
        leaveViewModel = new CoursePromptViewModel();

        presenter = new CoursePromptPresenter(createViewModel, joinViewModel, leaveViewModel);
    }

    @Test
    public void testCreateCourseValid() {
        CreateCourseOutputData outputData = new CreateCourseOutputData(
                true,
                "Good Test"
        );

        presenter.present(outputData);

        Assertions.assertTrue(createViewModel.getResponse());
        Assertions.assertEquals("Good Test", createViewModel.getMessage());
    }

    @Test
    public void testCreateCourseExisting() {
        CreateCourseOutputData outputData = new CreateCourseOutputData(
                false,
                "That course exists already."
        );

        presenter.present(outputData);

        Assertions.assertFalse(createViewModel.getResponse());
        Assertions.assertEquals("That course exists already.", createViewModel.getMessage());
    }

    @Test
    public void testJoinCourseValid() {
        JoinCourseOutputData outputData = new JoinCourseOutputData(
                true,
                "Good Test"
        );

        presenter.present(outputData);

        Assertions.assertTrue(joinViewModel.getResponse());
        Assertions.assertEquals("Good Test", joinViewModel.getMessage());
    }

    @Test
    public void testJoinCourseNonExisting() {
        JoinCourseOutputData outputData = new JoinCourseOutputData(
                false,
                "That course does not exist."
        );

        presenter.present(outputData);

        Assertions.assertFalse(joinViewModel.getResponse());
        Assertions.assertEquals("That course does not exist.", joinViewModel.getMessage());
    }

    @Test
    public void testJoinCourseAlreadyIn() {
        JoinCourseOutputData outputData = new JoinCourseOutputData(
                false,
                "You are already in that course."
        );

        presenter.present(outputData);

        Assertions.assertFalse(joinViewModel.getResponse());
        Assertions.assertEquals("You are already in that course.", joinViewModel.getMessage());
    }

    @Test
    public void testLeaveCourseValid() {
        LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                true,
                "Good Test"
        );

        presenter.present(outputData);

        Assertions.assertTrue(leaveViewModel.getResponse());
        Assertions.assertEquals("Good Test", leaveViewModel.getMessage());
    }

    @Test
    public void testLeaveCourseNonExisting() {
        LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                false,
                "That course does not exist."
        );

        presenter.present(outputData);

        Assertions.assertFalse(leaveViewModel.getResponse());
        Assertions.assertEquals("That course does not exist.", leaveViewModel.getMessage());
    }

    @Test
    public void testLeaveCourseNotIn() {
        LeaveCourseOutputData outputData = new LeaveCourseOutputData(
                false,
                "You are not in that course."
        );

        presenter.present(outputData);

        Assertions.assertFalse(leaveViewModel.getResponse());
        Assertions.assertEquals("You are not in that course.", leaveViewModel.getMessage());
    }
}
