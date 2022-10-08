package com.newordle.newordle.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.newordle.newordle.services.NewordleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("")
@RestController
public class Controller {

    @GetMapping("/healthcheck/{pathParam}")
    public String healthCheck(@RequestBody String parameterCheck, @RequestParam int queryParam,
            @PathVariable String pathParam) {
        return parameterCheck + pathParam + queryParam;
    }

    @GetMapping("/newordle")
    public int[] healthCheck(@RequestParam String queryParam) {
        return NewordleService.getRes(queryParam);
    }
}