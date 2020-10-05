package com.example.feature.services;

import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;

import java.util.List;

public interface ProjectService{

    void create(FeatureDTO request);

    List<FeatureDTO> read();

    void update(FeatureUpdateRequest request);

    void delete(FeatureDTO request);
}
