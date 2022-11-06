package com.newordle.newordle.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.newordle.newordle.services.NewordleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("")
@RestController
public class Controller {

    @Autowired
    NewordleService service;

    // curl-X GET
    // localhost:8080/healthcheck/ABC?queryParam=4-H'Content-type:application/json'
    // -d"Hello
    @GetMapping("/healthcheck/{pathParam}")
    public String healthCheck(@RequestBody String parameterCheck, @RequestParam int queryParam,
            @PathVariable String pathParam) {
        service.setDailyWord();
        return parameterCheck + pathParam + queryParam;
    }

    // sample endpoint for first load of words to mongodb. to be deprecated.
    // @PutMapping("/insertAllWords")
    // public String insertAllWords() {
    // service.insertAllWords();
    // return "Ran success";
    // }

    // curl-X GET localhost:8080/newordle?queryParam=madam
    /*
     * --------TO DO--------
     * Find better name
     * Response Codes update
     */
    @GetMapping("/newordle")
    public String newordle(@RequestParam String queryParam) {
        if (!service.validateEnteredWord(queryParam)) {
            return "Invalid Word!";
        }
        int[] res = service.getResult(queryParam);
        String ret = "";
        for (int i = 0; i < 5; i++) {
            if (res[i] == 0) {
                ret = ret + "Grey";
            } else if (res[i] == 1) {
                ret = ret + "Yellow";
            } else {
                ret = ret + "Green";
            }
        }
        return ret;
    }

    // API to insert one new word
    /*
     * --------TO DO--------
     * Find better name
     * Response Codes update
     */
    @PutMapping("/insertWord")
    public String insertWord(@RequestParam String word) {
        return service.insertOneWord(word);
    }

    // sample api to check frontend
    @GetMapping("/frontend")
    public String frontend() {
        // service.setDailyWord();
        return "Set Success";
    }
}