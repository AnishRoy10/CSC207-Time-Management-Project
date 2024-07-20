//package use_case;
//
//import entity.TodoList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import repositories.TodoListRepository;
//import use_case.TodoListUseCases.AddTaskUseCase.AddTaskOutputBoundary;
//import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
//import use_case.TodoListUseCases.AddTaskUseCase.AddTaskResponseModel;
//import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class AddTaskUseCaseTest {
//    private TodoListRepository todoListRepository;
//    private AddTaskOutputBoundary addTaskOutputBoundary;
//    private AddTaskUseCase addTaskUseCase;
//
//    @BeforeEach
//    public void setUp() {
//        todoListRepository = mock(TodoListRepository.class);
//        addTaskOutputBoundary = mock(AddTaskOutputBoundary.class);
//        addTaskUseCase = new AddTaskUseCase(todoListRepository, addTaskOutputBoundary);
//    }
//
//    @Test
//    public void testExecute() {
//        // Test adding a single task
//        TodoList todoList = new TodoList();
//        when(todoListRepository.loadTodoList()).thenReturn(todoList);
//
//        AddTaskRequestModel requestModel = new AddTaskRequestModel(
//                "Title",
//                "Description",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusDays(1),
//                "Course"
//        );
//
//        addTaskUseCase.execute(requestModel);
//
//        verify(todoListRepository, times(1)).loadTodoList();
//        verify(todoListRepository, times(1)).saveTodoList(todoList);
//
//        ArgumentCaptor<AddTaskResponseModel> argumentCaptor = ArgumentCaptor.forClass(AddTaskResponseModel.class);
//        verify(addTaskOutputBoundary, times(1)).present(argumentCaptor.capture());
//
//        AddTaskResponseModel capturedResponseModel = argumentCaptor.getValue();
//        List<TaskData> tasks = capturedResponseModel.getTasks();
//        assertEquals(1, tasks.size());
//
//        TaskData taskData = tasks.get(0);
//        assertEquals(requestModel.getTitle(), taskData.getTitle());
//        assertEquals(requestModel.getDescription(), taskData.getDescription());
//        assertEquals(requestModel.getStartDate(), taskData.getStartDate());
//        assertEquals(requestModel.getDeadline(), taskData.getDeadline());
//        assertEquals(requestModel.getCourse(), taskData.getCourse());
//
//        assertEquals(1, todoList.getTasks().size());
//        TaskData addedTask = tasks.get(0);
//        assertEquals(requestModel.getTitle(), addedTask.getTitle());
//        assertEquals(requestModel.getDescription(), addedTask.getDescription());
//        assertEquals(requestModel.getStartDate(), addedTask.getStartDate());
//        assertEquals(requestModel.getDeadline(), addedTask.getDeadline());
//        assertEquals(requestModel.getCourse(), addedTask.getCourse());
//    }
//
//    @Test
//    public void testAddMultipleTasks() {
//        // Test adding multiple tasks
//        TodoList todoList = new TodoList();
//        when(todoListRepository.loadTodoList()).thenReturn(todoList);
//
//        AddTaskRequestModel requestModel1 = new AddTaskRequestModel(
//                "Title1",
//                "Description1",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusDays(1),
//                "Course1"
//        );
//
//        AddTaskRequestModel requestModel2 = new AddTaskRequestModel(
//                "Title2",
//                "Description2",
//                LocalDateTime.now(),
//                LocalDateTime.now().plusDays(2),
//                "Course2"
//        );
//
//        addTaskUseCase.execute(requestModel1);
//        addTaskUseCase.execute(requestModel2);
//
//        verify(todoListRepository, times(2)).loadTodoList();
//        verify(todoListRepository, times(2)).saveTodoList(todoList);
//
//        ArgumentCaptor<AddTaskResponseModel> argumentCaptor = ArgumentCaptor.forClass(AddTaskResponseModel.class);
//        verify(addTaskOutputBoundary, times(2)).present(argumentCaptor.capture());
//
//        AddTaskResponseModel capturedResponseModel = argumentCaptor.getAllValues().get(1);
//        List<TaskData> tasks = capturedResponseModel.getTasks();
//        assertEquals(2, tasks.size());
//
//        TaskData taskData1 = tasks.get(0);
//        TaskData taskData2 = tasks.get(1);
//
//        assertEquals(requestModel1.getTitle(), taskData1.getTitle());
//        assertEquals(requestModel1.getDescription(), taskData1.getDescription());
//        assertEquals(requestModel1.getStartDate(), taskData1.getStartDate());
//        assertEquals(requestModel1.getDeadline(), taskData1.getDeadline());
//        assertEquals(requestModel1.getCourse(), taskData1.getCourse());
//
//        assertEquals(requestModel2.getTitle(), taskData2.getTitle());
//        assertEquals(requestModel2.getDescription(), taskData2.getDescription());
//        assertEquals(requestModel2.getStartDate(), taskData2.getStartDate());
//        assertEquals(requestModel2.getDeadline(), taskData2.getDeadline());
//        assertEquals(requestModel2.getCourse(), taskData2.getCourse());
//
//        assertEquals(2, todoList.getTasks().size());
//    }
//
//    @Test
//    public void testAddTaskWithNullDescription() {
//        // Test adding a task with a null description
//        TodoList todoList = new TodoList();
//        when(todoListRepository.loadTodoList()).thenReturn(todoList);
//
//        AddTaskRequestModel requestModel = new AddTaskRequestModel(
//                "Title",
//                null,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusDays(1),
//                "Course"
//        );
//
//        addTaskUseCase.execute(requestModel);
//
//        verify(todoListRepository, times(1)).loadTodoList();
//        verify(todoListRepository, times(1)).saveTodoList(todoList);
//
//        ArgumentCaptor<AddTaskResponseModel> argumentCaptor = ArgumentCaptor.forClass(AddTaskResponseModel.class);
//        verify(addTaskOutputBoundary, times(1)).present(argumentCaptor.capture());
//
//        AddTaskResponseModel capturedResponseModel = argumentCaptor.getValue();
//        List<TaskData> tasks = capturedResponseModel.getTasks();
//        assertEquals(1, tasks.size());
//
//        TaskData taskData = tasks.get(0);
//        assertEquals(requestModel.getTitle(), taskData.getTitle());
//        assertEquals("", taskData.getDescription()); // Should be empty string if null
//        assertEquals(requestModel.getStartDate(), taskData.getStartDate());
//        assertEquals(requestModel.getDeadline(), taskData.getDeadline());
//        assertEquals(requestModel.getCourse(), taskData.getCourse());
//
//        assertEquals(1, todoList.getTasks().size());
//        TaskData addedTask = tasks.get(0);
//        assertEquals(requestModel.getTitle(), addedTask.getTitle());
//        assertEquals("", addedTask.getDescription()); // Should be empty string if null
//        assertEquals(requestModel.getStartDate(), addedTask.getStartDate());
//        assertEquals(requestModel.getDeadline(), addedTask.getDeadline());
//        assertEquals(requestModel.getCourse(), addedTask.getCourse());
//    }
//}
