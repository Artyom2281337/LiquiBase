package com.example.feature.repositories;

import com.example.feature.entity.Feature;
import com.example.feature.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByFullPath(String fullPath);
    List<Feature> findByType(Type type);
}
