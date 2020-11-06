package com.example.feature.controllers;

import com.example.feature.services.DestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DestController {

    private final DestService destService;

    @GetMapping("/dest/{name}&{type}")
    public String postData(@PathVariable String name, @PathVariable String type){
        destService.insertData(name, type);
        return "ok";
    }
}
