package com.example.feature.controllers;

import com.example.feature.entity.Feature;
import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;
import com.example.feature.services.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "feature", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureController {

    public final FeatureService featureService;

    @PostMapping("/create")
    public void createFeature(@Valid @RequestBody FeatureDTO request){
        featureService.create(request);
    }

    @GetMapping
    public FeatureDTO getFeature(@Valid @RequestBody FeatureDTO request){
        return featureService.read(request);
    }

    @PutMapping("/update")
    public void updateFeature(@Valid @RequestBody FeatureUpdateRequest request){
        featureService.update(request);
    }

    @DeleteMapping("/delete")
    public void deleteFeature(@Valid @RequestBody FeatureDTO request){
        featureService.delete(request);
    }
}
