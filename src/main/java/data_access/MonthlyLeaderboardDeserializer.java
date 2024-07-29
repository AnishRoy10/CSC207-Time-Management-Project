package data_access;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.MonthlyLeaderboard;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;

public class MonthlyLeaderboardDeserializer implements JsonDeserializer<MonthlyLeaderboard> {

    @Override
    public MonthlyLeaderboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        LocalDate month = context.deserialize(jsonObject.get("month"), LocalDate.class);
        Map<String, Integer> scores = context.deserialize(jsonObject.get("scores"), new TypeToken<Map<String, Integer>>() {}.getType());
        MonthlyLeaderboard leaderboard = new MonthlyLeaderboard(name, month);
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            leaderboard.addScore(entry.getKey(), entry.getValue());
        }
        return leaderboard;
    }
}
