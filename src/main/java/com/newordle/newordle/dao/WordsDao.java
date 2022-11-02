package com.newordle.newordle.dao;

import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.newordle.newordle.model.WordsDb;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Updates.*;

public class WordsDao {

    MongoClient mongoClient;

    // establishConnection sets the Mongo Client connection
    private void establishConnection() {
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://newordleadmin:WFiVDY6zw0Wtpcru@newordle.a043wfu.mongodb.net/?retryWrites=true&w=majority");
        mongoClient = MongoClients.create(connectionString);
    }

    // getDbCollection return the MongoDB Collection
    public MongoCollection<Document> getDbCollection() {

        try {
            establishConnection();
            MongoDatabase database = mongoClient.getDatabase("newordle");
            MongoCollection<Document> collection = database.getCollection("words");
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

    // terminateConnection sets the Mongo Client connection
    public void terminateConnection() {
        mongoClient.close();
    }

    // findWord returns the document corresponding to the _id passed as POJO. TBI
    public WordsDb findWordById(int _id) {
        try {
            MongoCollection<Document> collection = getDbCollection();
            Document foundWordDoc = collection.find(Filters.eq("_id", _id)).first();
            if (foundWordDoc == null) {
                return null;
            }
            return new WordsDb(_id, (String) foundWordDoc.get("word"), (Boolean) foundWordDoc.get("used"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            terminateConnection();
        }
    }

    // getAllWords returns set of all words from the DB
    public Set<String> getAllWords() {
        try {
            MongoCollection<Document> collection = getDbCollection();
            FindIterable<Document> iterDocs = collection.find();
            MongoCursor<Document> cursor = iterDocs.iterator();
            Set<String> wordSet = new HashSet<>();
            while (cursor.hasNext()) {
                wordSet.add((String) cursor.next().get("word"));
            }
            return wordSet;
        } catch (Exception e) {
            System.out.println("Error fetching words!");
            return null;
        } finally {
            terminateConnection();
        }
    }

    // setUsed sets the daily word's used status to true
    public void setUsed(int _id) {
        try {
            MongoCollection<Document> collection = getDbCollection();
            Bson update = set("used", true);
            Document foundWordDoc = collection.findOneAndUpdate(Filters.eq("_id", _id), update);
            if (foundWordDoc == null) {
                System.err.println("Failed to update status!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
    }
}
