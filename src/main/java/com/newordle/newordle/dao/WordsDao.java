package com.newordle.newordle.dao;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.newordle.newordle.model.WordsDb;

public class WordsDao {

    MongoClient mongoClient;

    // establishConnection sets the Mongo Client connection
    private void establishConnection() {
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://newordleadmin:ZygRC0kw17PiZRp@newordle.a043wfu.mongodb.net/?retryWrites=true&w=majority");

        mongoClient = MongoClients.create(connectionString);
    }

    // getDbCollection return the MongoDB Collection
    public MongoCollection<Document> getDbCollection() {

        try {
            establishConnection();
            MongoDatabase database = mongoClient.getDatabase("newordle");
            MongoCollection<Document> collection = database.getCollection("wordss");
            return collection;
        } catch (IllegalArgumentException iae) {
            System.out.println("Error Establishing Connection!");
            iae.printStackTrace();
            return null;
        } catch (MongoClientException mce) {
            System.out.println("Error Establishing Connection!");
            mce.printStackTrace();
            return null;
        } catch (MongoException me) {
            System.out.println("Error Fetching Collection!");
            me.printStackTrace();
            return null;
        }
    }

    // findWord returns the document corresponding to the _id passed as POJO. TBI
    public WordsDb findWordById(int _id) {
        return new WordsDb(1, "", true);
    }
}
