package com.newordle.newordle.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

@Service
public class NewordleService {
    static String dailyWord;
    static Map<Character, List<Integer>> dailyMap = new HashMap<>();
    static Set<String> wordSet = new HashSet<>();
    static Set<Integer> wordsUsedIndices = new HashSet<>();

    // NewordleService() empty constructor to create a char map for the daily word
    public NewordleService() {
        // Creating HashSet for list of words
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File wordFile = new File(classLoader.getResource("static/Words.txt").getFile());
            Scanner wordFileScanner = new Scanner(wordFile);
            String word;
            while (wordFileScanner.hasNextLine()) {
                word = wordFileScanner.nextLine().strip().toLowerCase();
                wordSet.add(word);
            }
            wordFileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setDailyWord();
        // Creating char map for dailyWord
        for (int i = 0; i < 5; i++) {
            char c = dailyWord.charAt(i);
            if (!dailyMap.containsKey(c)) {
                dailyMap.put(c, new ArrayList<>());
            }
            dailyMap.get(c).add(i);
        }

    }

    /*
     * Method to generate and set the daily word
     * Has to be set as a cron job
     */
    public void setDailyWord() {
        Random rand = new Random();
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File wordFile = new File(classLoader.getResource("static/Words.txt").getFile());
            Scanner wordFileScanner = new Scanner(wordFile);
            String word;
            int i = 0;
            int dailyWordIndex = rand.nextInt(5757);
            // Creating random till new word found
            while (wordsUsedIndices.contains(dailyWordIndex)) {
                dailyWordIndex = rand.nextInt(5757);
            }
            while (wordFileScanner.hasNextLine()) {
                i++;
                word = wordFileScanner.nextLine().strip().toLowerCase();
                if (i == dailyWordIndex) {
                    dailyWord = word;
                }
            }
            System.out.println("\n\n============XXXXX=============\n\n");
            System.out.println(dailyWord + " " + dailyWordIndex + "\n=====================");
            wordFileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // validateEnteredWord checks if the entered word is actually a word or not
    public Boolean validateEnteredWord(String enteredWord) {
        if (wordSet.contains(enteredWord)) {
            return true;
        }
        return false;
    }

    // 0-grey, 1-yellow, 2-green
    public int[] getResult(String entered) {
        Map<Character, List<Integer>> dailyMapCopy = new HashMap<>();
        dailyMapCopy = dailyMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, valueMapper -> new ArrayList<>(valueMapper.getValue())));

        int[] res = new int[5];
        for (int i = 0; i < 5; i++) {
            char c = dailyWord.charAt(i);
            if (c == entered.charAt(i)) {
                res[i] = 2;
                dailyMapCopy.get(c).remove(Integer.valueOf(i));
            }
        }
        // int a = enteredWordMap.get('a').stream().map().collect(Collectors.toList());

        // dailyMap contains all non green character positions only
        // res contains all green characters
        // now computing yellow and grey
        for (int i = 0; i < 5; i++) {
            if (res[i] == 0) {
                char c = entered.charAt(i);
                if (dailyMapCopy.containsKey(c) && !dailyMapCopy.get(c).isEmpty()) {
                    res[i] = 1;
                    dailyMapCopy.get(c).remove(0);
                } else {
                    res[i] = 0;
                }
            }
        }
        return res;
    }

    public void updateMongoCollection() {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File wordFile = new File(classLoader.getResource("static/Words.txt").getFile());
            Scanner wordFileScanner = new Scanner(wordFile);
            ConnectionString connectionString = new ConnectionString(
                    "mongodb+srv://newordleadmin:ZygRC0kwQ17PiZRp@newordle.a043wfu.mongodb.net/?retryWrites=true&w=majority");
            try {
                MongoClient mongoClient = MongoClients.create(connectionString);
                MongoDatabase database = mongoClient.getDatabase("newordle");
                MongoCollection<Document> collection = database.getCollection("words");
                String word;
                int i = 0;
                while (wordFileScanner.hasNextLine()) {
                    i++;
                    word = wordFileScanner.nextLine().strip().toLowerCase();
                    try {
                        InsertOneResult result = collection.insertOne(new Document()
                                .append("_id", new ObjectId())
                                .append("index", i)
                                .append("word", word));
                        System.out.println("Success! Inserted document id: " + result.getInsertedId());
                    } catch (MongoException me) {
                        System.err.println("Unable to insert due to an error: " + me);
                    }
                }
                wordFileScanner.close();
            } catch (MongoException mException) {
                System.out.println(mException);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}