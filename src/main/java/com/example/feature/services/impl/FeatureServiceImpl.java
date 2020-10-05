package com.example.feature.services.impl;

import com.example.feature.entity.Feature;
import com.example.feature.enums.Type;
import com.example.feature.exeptions.ForbiddenExeption;
import com.example.feature.exeptions.NotFoundExeption;
import com.example.feature.repositories.FeatureRepository;
import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;
import com.example.feature.services.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {

    public final FeatureRepository featureRepository;

    @Override
    @Transactional
    public void create(FeatureDTO request) {
        if (featureRepository.findByFullPath(request.getPath() + "/" + request.getName()).isPresent()) {
            throw new ForbiddenExeption();
        }

        Feature group = featureRepository.findByFullPath(request.getPath())
                .orElseThrow(NotFoundExeption::new);

        if (group.getType() == Type.FEATURE || group.getType() == Type.PROJECT){
            throw new ForbiddenExeption();
        }

        Feature feature = createFeature(request);
        featureRepository.save(feature);

        group.getChilds().add(feature);
        featureRepository.save(feature);
    }

    @Override
    public FeatureDTO read(FeatureDTO request) {
        return featureRepository.findByFullPath(request.getPath() + "/" + request.getName())
                .map(this::createFeatureDTO).orElseThrow(NotFoundExeption::new);
    }

    @Override
    public void update(FeatureUpdateRequest request) {
        Feature feature = featureRepository.findByFullPath(request.getFullPath())
                .orElseThrow(NotFoundExeption::new);

        feature.setName(request.getName());
        feature.setFullPath(request.getPath() + "/" + request.getName());
        feature.setIsActive(request.getIsActive());

        featureRepository.save(feature);
    }

    @Override
    public void delete(FeatureDTO request) {
        Feature feature = featureRepository
                .findByFullPath(request.getPath() + "/" + request.getName())
                .orElseThrow(NotFoundExeption::new);

        featureRepository.findByFullPath(request.getPath()).orElseThrow(NotFoundExeption::new)
                .getChilds().remove(feature);
        featureRepository.delete(feature);
    }



    private Feature createFeature(FeatureDTO request){
        Feature feature = new Feature();

        feature.setName(request.getName());
        feature.setPath(request.getPath());
        feature.setFullPath(request.getPath() + "/" + request.getName());
        feature.setType(Type.FEATURE);
        feature.setIsActive(request.getIsActive());

        return feature;
    }

    private FeatureDTO createFeatureDTO(Feature feature){
        FeatureDTO featureDTO = new FeatureDTO();

        featureDTO.setName(feature.getName());
        featureDTO.setPath(feature.getPath());
        featureDTO.setIsActive(feature.getIsActive());

        return featureDTO;
    }
}
