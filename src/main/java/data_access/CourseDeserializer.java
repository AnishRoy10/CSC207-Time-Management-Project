package data_access;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseDeserializer implements JsonDeserializer<Course> {
    @Override
    public Course deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();

        String name = jsonObj.get("name").getAsString();
        String description = jsonObj.get("description").getAsString();

        List<String> usernames = new ArrayList<>();
        JsonArray jsonUsernames = jsonObj.get("usernames").getAsJsonArray();
        for (int i = 0; i < jsonUsernames.size(); i++) {
            usernames.add(jsonUsernames.get(i).getAsString());
        }
        Course course = new Course(name, description);

        course.setUsernames(usernames);
        course.setTodoList(jsonDeserializationContext.deserialize(jsonObj.get("todolist"), TodoList.class));
        course.setDailyLeaderboard(jsonDeserializationContext.deserialize(jsonObj.get("dailyLeaderboard"), DailyLeaderboard.class));
        course.setMonthlyLeaderboard(jsonDeserializationContext.deserialize(jsonObj.get("monthlyLeaderboard"), MonthlyLeaderboard.class));
        course.setAllTimeLeaderboard(jsonDeserializationContext.deserialize(jsonObj.get("allTimeLeaderboard"), AllTimeLeaderboard.class));

        return course;
    }
}
