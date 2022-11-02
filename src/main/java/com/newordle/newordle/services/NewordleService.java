package com.newordle.newordle.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.newordle.newordle.dao.WordsDao;
import com.newordle.newordle.model.WordsDb;

@Service
public class NewordleService {
    static String dailyWord;
    static Map<Character, List<Integer>> dailyMap = new HashMap<>();
    static Set<String> wordSet = new HashSet<>();
    WordsDao wordsDao = new WordsDao();

    // NewordleService() empty constructor to create a char map for the daily word
    public NewordleService() {

        wordSet = wordsDao.getAllWords();
        if (wordSet == null) {
            System.err.println("ERROR: Wordset not created");
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
        int dailyWordIndex = rand.nextInt(5757);
        WordsDb wordObj;
        wordObj = wordsDao.findWordById(dailyWordIndex);
        // Creating random till new word found
        while (wordObj.getUsedStatus()) {
            dailyWordIndex = rand.nextInt(5757);
            wordObj = wordsDao.findWordById(dailyWordIndex);
        }
        dailyWord = wordObj.getWord();
        wordsDao.setUsed(dailyWordIndex);
        System.out.println("\n\n============XXXXX=============\n\n");
        System.out.println(dailyWord + " " + dailyWordIndex + "\n=====================");
    }

    // validateEnteredWord checks if the entered word is actually a word or not
    public Boolean validateEnteredWord(String enteredWord) {
        if (wordSet.contains(enteredWord)) {
            return true;
        }
        return false;
    }

    // getResult checks the entered word against the daily word
    public int[] getResult(String enteredWord) {
        Map<Character, List<Integer>> dailyMapCopy = new HashMap<>();
        dailyMapCopy = dailyMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, valueMapper -> new ArrayList<>(valueMapper.getValue())));

        int[] res = new int[5];
        // 0-grey, 1-yellow, 2-green
        for (int i = 0; i < 5; i++) {
            char c = dailyWord.charAt(i);
            if (c == enteredWord.charAt(i)) {
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
                char c = enteredWord.charAt(i);
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

    // updateMongoCollection adds words for first time to mongoDB collection
    public void updateMongoCollection() {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File wordFile = new File(classLoader.getResource("static/Words.txt").getFile());
            Scanner wordFileScanner = new Scanner(wordFile);
            // System.out.println(wordsDao.findWordById(1));
            // System.out.println(wordsDao.getAllWords());
            MongoCollection<Document> collection = wordsDao.getDbCollection();
            if (collection == null) {
                System.out.println("Failed to insert!");
                wordFileScanner.close();
                return;
            }
            System.out.println(collection.countDocuments());

            String word;
            int i = 0;
            Document doc;
            while (wordFileScanner.hasNextLine()) {
                i++;
                word = wordFileScanner.nextLine().strip().toLowerCase();
                try {
                    doc = new Document().append("_id", i)
                            .append("used", false)
                            .append("word", word);
                    InsertOneResult result = collection.insertOne(doc);
                    System.out.println("Success! Inserted document id: " +
                            result.getInsertedId());

                } catch (MongoException me) {
                    System.err.println("Unable to insert due to an error: " + me);
                }
            }
            wordFileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            wordsDao.terminateConnection();
        }
    }
}