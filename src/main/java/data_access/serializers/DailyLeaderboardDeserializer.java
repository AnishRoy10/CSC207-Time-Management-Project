package data_access.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.DailyLeaderboard;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;

public class DailyLeaderboardDeserializer implements JsonDeserializer<DailyLeaderboard> {

    @Override
    public DailyLeaderboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        LocalDate currentDate = context.deserialize(jsonObject.get("currentDate"), LocalDate.class);
        Map<String, Integer> scores = context.deserialize(jsonObject.get("scores"), new TypeToken<Map<String, Integer>>() {}.getType());
        DailyLeaderboard leaderboard = new DailyLeaderboard(name, currentDate);
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            leaderboard.addScore(entry.getKey(), entry.getValue());
        }
        return leaderboard;
    }
}
