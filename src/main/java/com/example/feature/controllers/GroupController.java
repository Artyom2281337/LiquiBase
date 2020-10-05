package com.example.feature.controllers;

import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;
import com.example.feature.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "group", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {

    public final GroupService groupService;

    @PostMapping("/create")
    public void createGroup(@Valid @RequestBody FeatureDTO request){
        groupService.create(request);
    }

    @GetMapping
    public FeatureDTO getGroup(@Valid @RequestBody FeatureDTO request){
        return groupService.read(request);
    }

    @PutMapping("/update")
    public void updateGroup(@Valid @RequestBody FeatureUpdateRequest request){
        groupService.update(request);
    }

    @DeleteMapping("/delete")
    public void deleteGroup(@Valid @RequestBody FeatureDTO request){
        groupService.delete(request);
    }
}
