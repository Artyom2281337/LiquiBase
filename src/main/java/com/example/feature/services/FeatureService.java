package com.example.feature.services;

import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;

import java.util.List;

public interface FeatureService {

    void create(FeatureDTO request);

    FeatureDTO read(FeatureDTO request);

    void update(FeatureUpdateRequest request);

    void delete(FeatureDTO request);

}
