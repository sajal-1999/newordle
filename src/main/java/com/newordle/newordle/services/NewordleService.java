package com.newordle.newordle.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class NewordleService {
    String dailyWord = "madam";
    static Map<Character, List<Integer>> dailyMap = new HashMap<>();

    // NewordleService() empty constructor to create a char map for the daily word
    public NewordleService() {
        for (int i = 0; i < 5; i++) {
            char c = this.dailyWord.charAt(i);
            if (!NewordleService.dailyMap.containsKey(c))
                NewordleService.dailyMap.put(c, new ArrayList<>());
            NewordleService.dailyMap.get(c).add(i);
        }
    }

    /*
     * Method to generate and set the daily word
     * Currently hardcoded the value
     * Has to be set as a random generator as a cron job
     */
    public void setDailyWord() {
        this.dailyWord = "madam";
    }

    // validateEnteredWord checks if the entered word is actually a word or not
    public Boolean validateEnteredWord(String enteredWord) {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File wordFile = new File(classLoader.getResource("static/Words.txt").getFile());
            Scanner scanner = new Scanner(wordFile);
            String word;
            while (scanner.hasNextLine()) {
                word = scanner.nextLine().strip().toLowerCase();
                if (word.equalsIgnoreCase(enteredWord)) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 0-grey, 1-yellow, 2-green
    public int[] getResult(String entered) {
        Map<Character, List<Integer>> dailyMapCopy = new HashMap<>();
        dailyMapCopy.putAll(NewordleService.dailyMap);

        int[] res = new int[5];
        for (int i = 0; i < 5; i++) {
            char c = this.dailyWord.charAt(i);
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
}