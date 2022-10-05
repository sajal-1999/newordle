package com.newordle.newordle.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {

    @GetMapping("/healthcheck/{pathParam}")
    public String healthCheck(@RequestBody String parameterCheck, @RequestParam int queryParam,
            @PathVariable String pathParam) {
        return parameterCheck + pathParam + queryParam;
    }
}