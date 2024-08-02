package data_access.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.AllTimeLeaderboard;

import java.lang.reflect.Type;
import java.util.Map;

public class AllTimeLeaderboardDeserializer implements JsonDeserializer<AllTimeLeaderboard> {

    @Override
    public AllTimeLeaderboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        Map<String, Integer> scores = context.deserialize(jsonObject.get("scores"), new TypeToken<Map<String, Integer>>() {}.getType());
        AllTimeLeaderboard leaderboard = new AllTimeLeaderboard(name);
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            leaderboard.addScore(entry.getKey(), entry.getValue());
        }
        return leaderboard;
    }
}
