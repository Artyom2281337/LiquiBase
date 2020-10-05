package com.example.feature.services.impl;

import com.example.feature.entity.Feature;
import com.example.feature.enums.Type;
import com.example.feature.exeptions.ForbiddenExeption;
import com.example.feature.exeptions.NotFoundExeption;
import com.example.feature.repositories.FeatureRepository;
import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;
import com.example.feature.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    public final FeatureRepository featureRepository;

    @Override
    @Transactional
    public void create(FeatureDTO request) {
        if (featureRepository.findByFullPath(request.getPath() + "/" + request.getName()).isPresent()) {
            throw new ForbiddenExeption();
        }

        Feature feature = featureRepository.findByFullPath(request.getPath())
                .orElseThrow(NotFoundExeption::new);

        if (feature.getType() == Type.FEATURE){
            throw new ForbiddenExeption();
        }

        Feature group = createGroup(request);
        featureRepository.save(group);

        feature.getChilds().add(group);
        featureRepository.save(feature);
    }

    @Override
    public FeatureDTO read(FeatureDTO request) {
        return featureRepository.findByFullPath(request.getPath() + "/" + request.getName())
                .map(this::createGroupDTO).orElseThrow(NotFoundExeption::new);
    }

    @Override
    public void update(FeatureUpdateRequest request) {
        Feature group = featureRepository.findByFullPath(request.getFullPath())
                .orElseThrow(NotFoundExeption::new);

        group.setName(request.getName());
        group.setFullPath(group.getFullPath().replaceFirst(request.getFullPath(),
                request.getPath()) + "/" + request.getName());

        featureRepository.save(group);

        featureRepository.findAll().stream().filter(feature ->
                feature.getFullPath().startsWith(request.getFullPath() + "/"))
                .forEach(feature -> updatePathAndFullPath(feature, request));
    }

    @Override
    public void delete(FeatureDTO request) {
        Feature group = featureRepository
                .findByFullPath(request.getPath() + "/" + request.getName())
                .orElseThrow(NotFoundExeption::new);

        featureRepository.findByFullPath(request.getPath()).orElseThrow(NotFoundExeption::new)
                .getChilds().remove(group);
        featureRepository.delete(group);
    }

    private Feature createGroup(FeatureDTO request){
        Feature group = new Feature();
        group.setName(request.getName());
        group.setPath(request.getPath());
        group.setFullPath(request.getPath() + "/" + request.getName());
        group.setType(Type.GROUP);
        return group;
    }

    private FeatureDTO createGroupDTOWithoutChilds(Feature group){
        FeatureDTO groupDTO = new FeatureDTO();
        groupDTO.setName(group.getName());
        groupDTO.setPath(group.getPath());
        return groupDTO;
    }

    private FeatureDTO createGroupDTO(Feature group){
        FeatureDTO groupDTO = createGroupDTOWithoutChilds(group);

        groupDTO.setFeatureList(group.getChilds().stream().map(this::createGroupDTOWithoutChilds)
        .collect(Collectors.toList()));

        return groupDTO;
    }

    private void updatePathAndFullPath(Feature group, FeatureUpdateRequest request){
        group.setPath(group.getPath().replaceFirst(request.getFullPath(),
                request.getPath() + "/" + request.getName()));

        group.setFullPath(group.getFullPath().replaceFirst(request.getFullPath(),
                request.getPath() + "/" + request.getName()));
        featureRepository.save(group);
    }
}
