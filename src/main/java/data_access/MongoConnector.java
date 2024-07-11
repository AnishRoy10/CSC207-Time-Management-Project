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
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoConnector() {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://admin1:fn5Z7JJgnYVLeLhq@cluster0.d61osh2.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

        MongoDatabase db = mongoClient.getDatabase("TimeManagementProject");

        MongoCollection col = db.getCollection("users");

        this.mongoClient = mongoClient;
        this.database = db;
        this.collection = col;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
