package com.newordle.newordle.services;

import java.util.*;

public class NewordleService {
    // 0-grey, 1-yellow, 2-green
    public static int[] getRes(String entered) {
        String dailyWord = "mamma";
        Map<Character, List<Integer>> dailyMap = new HashMap<>();
        int[] res = new int[5];
        for (int i = 0; i < 5; i++) {
            char c = dailyWord.charAt(i);
            if (!dailyMap.containsKey(c))
                dailyMap.put(c, new ArrayList<>());
            dailyMap.get(c).add(i);
            if (c == entered.charAt(i)) {
                res[i] = 2;
                dailyMap.get(c).remove(Integer.valueOf(i));
            }
        }

        // dailyMap contains all non green character positions only
        // res contains all green characters
        // now computing yellow and grey
        for (int i = 0; i < 5; i++) {
            if (res[i] == 0) {
                char c = entered.charAt(i);
                if (dailyMap.containsKey(c) && !dailyMap.get(c).isEmpty()) {
                    res[i] = 1;
                    dailyMap.get(c).remove(0);
                } else {
                    res[i] = 0;
                }
            }
        }
        return res;
    }
}