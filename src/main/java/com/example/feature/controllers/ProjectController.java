package com.example.feature.controllers;

import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;
import com.example.feature.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public void createProject(@Valid @RequestBody FeatureDTO request){
        projectService.create(request);
    }

    @GetMapping("/all")
    public List<FeatureDTO> getProjects(){
        return projectService.read();
    }

    @PutMapping("/update")
    public void updateProject(@Valid @RequestBody FeatureUpdateRequest request){
        projectService.update(request);
    }

    @DeleteMapping("/delete")
    public void deleteProject(@Valid @RequestBody FeatureDTO request){
        projectService.delete(request);
    }
}
