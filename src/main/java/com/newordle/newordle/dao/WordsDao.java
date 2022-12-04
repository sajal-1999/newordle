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
import com.mongodb.client.result.InsertOneResult;
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
    private MongoCollection<Document> getDbCollection() {

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
    private void terminateConnection() {
        mongoClient.close();
    }

    // getDailyWord
    public String getDailyWord() {
        try {
            MongoCollection<Document> collection = getDbCollection();
            Document dailyWordDoc = collection.find(Filters.eq("_id", 0)).first();
            if (dailyWordDoc == null) {
                return null;
            }
            return (String) dailyWordDoc.get("word");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            terminateConnection();
        }
    }

    // updateDailyWordDb
    public void updateDailyWordDb(String dailyWord) {
        try {
            MongoCollection<Document> collection = getDbCollection();
            Bson update = set("word", dailyWord);
            Document dailyWordDoc = collection.findOneAndUpdate(Filters.eq("_id", 0), update);
            if (dailyWordDoc == null) {
                System.err.println("Failed to update daily word in DB!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
    }

    // findWord returns the document corresponding to the _id passed as POJO
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
        Set<String> wordSet = null;
        try {
            MongoCollection<Document> collection = getDbCollection();
            FindIterable<Document> iterDocs = collection.find();
            MongoCursor<Document> cursor = iterDocs.iterator();
            wordSet = new HashSet<>();
            while (cursor.hasNext()) {
                wordSet.add((String) cursor.next().get("word"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching words!");
        } finally {
            terminateConnection();
        }
        return wordSet;
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

    // insertOneWord sets the daily word's used status to true
    public String insertOneWord(String word) {
        String ret = "";
        try {
            MongoCollection<Document> collection = getDbCollection();
            try {
                InsertOneResult result = collection.insertOne(new Document()
                        .append("_id", getWordCount() + 1)
                        .append("used", false)
                        .append("word", word));
                System.out.println("Success! Inserted document id: " + result.getInsertedId());
            } catch (MongoException me) {
                System.err.println("Unable to insert due to an error: " + me);
                ret = "Error Inserting";
            }
            ret = "Insertion successful";
        } catch (Exception e) {
            ret = "Error Inserting";
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return ret;
    }

    // getWordCount sets the daily word's used status to true
    public int getWordCount() {
        int wordCount = -1;
        try {
            MongoCollection<Document> collection = getDbCollection();
            wordCount = (int) collection.countDocuments();
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
        return wordCount;
    }
}