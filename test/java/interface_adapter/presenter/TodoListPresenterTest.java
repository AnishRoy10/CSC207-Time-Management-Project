package interface_adapter.presenter;

import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TodoListUseCases.TaskData;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskResponseModel;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskResponseModel;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListResponseModel;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskResponseModel;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksResponseModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoListPresenterTest {
    private TodoListPresenter presenter;
    private TodoListViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = new TodoListViewModel();
        presenter = new TodoListPresenter(viewModel);
    }

    @Test
    public void testPresentAddTask() {
        // Create TaskData
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        TaskData task1 = new TaskData(uuid1, "testUser1", "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course1", null);
        TaskData task2 = new TaskData(uuid2, "testUser2", "Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), false, "Course2", null);

        // Create AddTaskResponseModel
        AddTaskResponseModel responseModel = new AddTaskResponseModel(Arrays.asList(task1, task2));

        // Present the response
        presenter.present(responseModel);

        // Verify the ViewModel
        assertEquals(2, viewModel.getTasks().size());
        assertEquals("Title1", viewModel.getTasks().get(0).getTitle());
        assertEquals("Title2", viewModel.getTasks().get(1).getTitle());
    }

    @Test
    public void testPresentRemoveTask() {
        // Create TaskData
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        TaskData task1 = new TaskData(uuid1, "testUser1", "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course1", null);
        TaskData task2 = new TaskData(uuid2, "testUser2", "Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), false, "Course2", null);

        // Create RemoveTaskResponseModel
        RemoveTaskResponseModel responseModel = new RemoveTaskResponseModel(Arrays.asList(task1, task2));

        // Present the response
        presenter.present(responseModel);

        // Verify the ViewModel
        assertEquals(2, viewModel.getTasks().size());
        assertEquals("Title1", viewModel.getTasks().get(0).getTitle());
        assertEquals("Title2", viewModel.getTasks().get(1).getTitle());
    }

    @Test
    public void testPresentCompleteTask() {
        // Create TaskData
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        TaskData task1 = new TaskData(uuid1, "testUser1", "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course1", null);
        TaskData task2 = new TaskData(uuid2, "testUser2", "Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), true, "Course2", LocalDateTime.now());

        // Initialize ViewModel with tasks
        viewModel.setTasks(Arrays.asList(task1, task2));

        // Create CompleteTaskResponseModel
        CompleteTaskResponseModel responseModel = new CompleteTaskResponseModel(task2);

        // Present the response
        presenter.present(responseModel);

        // Verify the ViewModel
        assertEquals(2, viewModel.getTasks().size());
        assertEquals("Title1", viewModel.getTasks().get(0).getTitle());
        assertEquals("Title2", viewModel.getTasks().get(1).getTitle());
        assertEquals(true, viewModel.getTasks().get(1).isCompleted());
    }

    @Test
    public void testPresentSortTasks() {
        // Create TaskData
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        TaskData task1 = new TaskData(uuid1, "testUser1", "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course1", null);
        TaskData task2 = new TaskData(uuid2, "testUser2", "Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), false, "Course2", null);

        // Create SortTasksResponseModel
        SortTasksResponseModel responseModel = new SortTasksResponseModel(Arrays.asList(task1, task2));

        // Present the response
        presenter.present(responseModel);

        // Verify the ViewModel
        assertEquals(2, viewModel.getTasks().size());
        assertEquals("Title1", viewModel.getTasks().get(0).getTitle());
        assertEquals("Title2", viewModel.getTasks().get(1).getTitle());
    }

    @Test
    public void testPresentLoadTodoList() {
        // Create TaskData
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        TaskData task1 = new TaskData(uuid1, "testUser1", "Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course1", null);
        TaskData task2 = new TaskData(uuid2, "testUser2", "Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), false, "Course2", null);

        // Create LoadTodoListResponseModel
        LoadTodoListResponseModel responseModel = new LoadTodoListResponseModel(Arrays.asList(task1, task2));

        // Present the response
        presenter.present(responseModel);

        // Verify the ViewModel
        assertEquals(2, viewModel.getTasks().size());
        assertEquals("Title1", viewModel.getTasks().get(0).getTitle());
        assertEquals("Title2", viewModel.getTasks().get(1).getTitle());
    }
}
