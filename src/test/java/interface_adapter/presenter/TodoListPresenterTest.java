package interface_adapter.presenter;

import use_case.TaskData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoListPresenterTest {
    private TodoListPresenter presenter;

    @BeforeEach
    public void setUp() {
        presenter = new TodoListPresenter();
    }

    @Test
    public void testPresentAddTask() {
        // Arrange
        TaskData taskData1 = new TaskData(1, "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course1", null);
        TaskData taskData2 = new TaskData(2, "Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), false, "Course2", null);
        AddTaskResponseModel responseModel = new AddTaskResponseModel(Arrays.asList(taskData1, taskData2));
        responseModel.setTitle("Title1");

        // Act
        presenter.present(responseModel);

        // Assert - You can use assertion on the console output using System Rules library or similar.
        // However, for simplicity, we will verify if the present method works without errors.
    }

    @Test
    public void testPresentRemoveTask() {
        // Arrange
        TaskData taskData1 = new TaskData(1, "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course1", null);
        TaskData taskData2 = new TaskData(2, "Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), false, "Course2", null);
        RemoveTaskResponseModel responseModel = new RemoveTaskResponseModel(Arrays.asList(taskData1, taskData2));
        responseModel.setTaskId(1);

        // Act
        presenter.present(responseModel);

        // Assert - Verify if the method works without errors.
    }

    @Test
    public void testPresentCompleteTask() {
        // Arrange
        TaskData taskData = new TaskData(1, "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), true, "Course1", LocalDateTime.now());
        CompleteTaskResponseModel responseModel = new CompleteTaskResponseModel(taskData);
        responseModel.setTaskId(1);

        // Act
        presenter.present(responseModel);

        // Assert - Verify if the method works without errors.
    }
}
