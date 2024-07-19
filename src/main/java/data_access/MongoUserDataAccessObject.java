package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;

import static com.mongodb.client.model.Filters.eq;
//import entity.User;

public class MongoUserDataAccessObject {
    //private User user;
    private MongoConnector mongoConnector;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    public MongoUserDataAccessObject() {
        this.mongoConnector = new MongoConnector();
        this.mongoClient = this.mongoConnector.getMongoClient();
        this.mongoDatabase = this.mongoConnector.getDatabase();
        this.mongoCollection = this.mongoConnector.getCollection();
    }

    public HashMap<String, Object> getUser(String findusername) {
        HashMap<String, Object> userdata = new HashMap<String, Object>();
        Document userdoc = this.mongoCollection.find(eq("username", findusername)).first();
        assert userdoc != null;
        userdata.put("username", userdoc.getString("username"));
        userdata.put("friendsList", userdoc.toBsonDocument().getArray("friendsList"));
        userdata.put("score", userdoc.getInteger("score"));
        userdata.put("tasks", userdoc.toBsonDocument().getArray("tasks"));
        userdata.put("events", userdoc.toBsonDocument().getArray("events"));
        return userdata;
    }
}

