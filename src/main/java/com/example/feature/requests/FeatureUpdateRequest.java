package com.example.feature.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FeatureUpdateRequest {

    @NotBlank
    private String name;

    @NotNull
    private String path;

    @NotBlank
    private String fullPath;

    private Boolean isActive = null;

    private List<FeatureDTO> featureList;
}
