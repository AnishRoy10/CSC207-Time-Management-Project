package data_access;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.AllTimeLeaderboard;
import entity.Leaderboard;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Deserializer for AllTimeLeaderboard objects.
 * This class implements the JsonDeserializer interface.
 */
public class AllTimeLeaderboardDeserializer implements JsonDeserializer<AllTimeLeaderboard> {

    /**
     * Deserializes the specified JSON into an AllTimeLeaderboard object.
     * @param json the JSON data being deserialized
     * @param typeOfT the type of the Object to deserialize to
     * @param context the context of the deserialization process
     * @return an AllTimeLeaderboard object
     * @throws JsonParseException if JSON is not in the expected format
     */
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
