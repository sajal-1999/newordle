package com.newordle.newordle.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.newordle.newordle.dao.WordsDao;
import com.newordle.newordle.model.WordsDb;

@EnableScheduling
@Service
public class NewordleService {
    static String dailyWord;
    static Map<Character, List<Integer>> dailyMap = new HashMap<>();
    static Set<String> wordSet = new HashSet<>();
    WordsDao wordsDao = new WordsDao();

    // NewordleService() empty constructor to create a char map for the daily word
    public NewordleService() {
        insertAllWords();
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
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    public void setDailyWord() {
        Random rand = new Random();
        int wordCount = wordsDao.getWordCount();
        int dailyWordIndex = rand.nextInt(wordCount);

        WordsDb wordObj;
        wordObj = wordsDao.findWordById(dailyWordIndex);

        // Creating random till new word found
        while (wordObj.getUsedStatus()) {
            dailyWordIndex = rand.nextInt(wordCount);
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

    // insertAllWords adds words for first time to mongoDB collection
    public void insertAllWords() {
        if (wordsDao.getWordCount() == 0) {
            System.out.println("DB Already has words!");
            return;
        }
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File wordFile = new File(classLoader.getResource("static/Words.txt").getFile());
            Scanner wordFileScanner = new Scanner(wordFile);

            String word;
            while (wordFileScanner.hasNextLine()) {
                word = wordFileScanner.nextLine().strip().toLowerCase();
                if (wordSet.contains(word)) {
                    continue;
                }
                System.out.println(insertOneWord(word));
            }
            wordFileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // insertOneWord is used to add a new word to the db
    public String insertOneWord(String word) {
        word = word.strip().toLowerCase();
        if (word.length() != 5) {
            return "Word Length not 5";
        }
        wordSet = wordsDao.getAllWords();
        if (wordSet.contains(word)) {
            return "Word already in the DB";
        }
        String insert = wordsDao.insertOneWord(word);
        if (insert == "Error Inserting") {
            return "Error Inserting";
        }
        wordSet.add(word);
        return "Successfully Inserted word - " + word;

    }
}
