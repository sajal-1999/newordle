package com.newordle.newordle.services;

import java.util.*;

public class NewordleService {
    String dailyWord = "madam";
    static Map<Character, List<Integer>> dailyMap = new HashMap<>();

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
    public void setDailyWord(String word) {
        this.dailyWord = "madam";
    }

    /*
     * Placeholder function to validate the entered word
     */
    public Boolean validateEnteredWord(String enteredWord) {
        if (enteredWord.equals(this.dailyWord)) {
            return true;
        } else {
            return false;
        }
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