package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.TodoList;
import repositories.TodoListRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO for storing and retrieving TodoLists from a JSON file.
 */
public class TodoListDataAccessObject implements TodoListRepository {
    private final File fileCache;
    private final Gson gson;

    public TodoListDataAccessObject(String filePath) throws IOException {
        this.fileCache = new File(filePath);
        if (!fileCache.exists()) {
            fileCache.createNewFile();
        }
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public TodoList loadTodoList(String username) throws IOException {
        if (fileCache.length() == 0) {
            return new TodoList();
        }
        try (Reader reader = new FileReader(fileCache)) {
            Type todoListType = new TypeToken<Map<String, TodoList>>() {}.getType();
            Map<String, TodoList> todoMap = gson.fromJson(reader, todoListType);
            return todoMap.getOrDefault(username, new TodoList());
        }
    }

    @Override
    public void saveTodoList(String username, TodoList todoList) throws IOException {
        Map<String, TodoList> todoMap = new HashMap<>();
        todoMap.put(username, todoList);
        try (Writer writer = new FileWriter(fileCache)) {
            gson.toJson(todoMap, writer);
        }
    }
}
