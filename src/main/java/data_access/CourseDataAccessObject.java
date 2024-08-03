package data_access;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import entity.*;
import repositories.CourseRepository;

public class CourseDataAccessObject implements CourseRepository {
    public static ArrayList<Course> courses = new ArrayList<>();

    private final File fileCache;
    private final Gson gson;

    public CourseDataAccessObject(String path) throws IOException {
        this.fileCache = new File(path);
        if (!fileCache.exists()) {
            if (!fileCache.createNewFile()) {
                throw new IOException("Something went wrong creating a course cache file.");
            }
            try (Writer writer = new FileWriter(fileCache)) {
                writer.write("{}");
            }
        }
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(Course.class, new CourseDeserializer())
                .registerTypeAdapter(AllTimeLeaderboard.class, new AllTimeLeaderboardDeserializer())
                .registerTypeAdapter(DailyLeaderboard.class, new DailyLeaderboardDeserializer())
                .registerTypeAdapter(MonthlyLeaderboard.class, new MonthlyLeaderboardDeserializer())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public Map<String, Course> ReadFromCache() throws IOException {
        if (fileCache.length() == 0) {
            try (Writer writer = new FileWriter(fileCache)) {
                writer.write("{}");
            }
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(fileCache)) {
            Type courseType = new TypeToken<Map<String, JsonObject>>() {
            }.getType();
            Map<String, JsonObject> jsonMap = gson.fromJson(reader, courseType);
            Map<String, Course> courses = new HashMap<>();
            for (Map.Entry<String, JsonObject> entry : jsonMap.entrySet()) {
                JsonObject courseJson = entry.getValue();
                Course course = gson.fromJson(courseJson, Course.class);
                courses.put(entry.getKey(), course);
            }
            System.out.println("Courses read from cache: " + courses);
            return courses;
        }
    }

    @Override
    public boolean WriteToCache(Course course) {
        try (Writer writer = new FileWriter(fileCache)) {
            JsonObject json = new JsonObject();
            JsonObject courseJson = gson.toJsonTree(course).getAsJsonObject();
            json.add(course.getName(), courseJson);
            gson.toJson(json, writer);
            System.out.println("Courses written to cache: " + json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean courseExists(String courseName) {
        try {
            return ReadFromCache().containsKey(courseName);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Course findByName(String courseName) {
        try {
            Map<String, Course> courses = ReadFromCache();
            if (courses.containsKey(courseName)) {
                return courses.get(courseName);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
}
