package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoConnector {
    /**
     * The MongoConnector class establishes a connection between the application and the MongoDB database
     *
     */
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://admin1:<password>@cluster0.d61osh2.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

        MongoDatabase db = mongoClient.getDatabase("TimeManagementProject");

        MongoCollection col = db.getCollection("users");

        Document sampleDoc = new Document("_id", new ObjectId("889f445ed1cb36fdc66b17ef")).append("username", "User2");

        col.insertOne(sampleDoc);
    }
}
