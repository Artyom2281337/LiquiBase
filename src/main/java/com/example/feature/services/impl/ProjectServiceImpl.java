package com.example.feature.services.impl;

import com.example.feature.entity.Feature;
import com.example.feature.enums.Type;
import com.example.feature.exeptions.ForbiddenExeption;
import com.example.feature.exeptions.NotFoundExeption;
import com.example.feature.repositories.FeatureRepository;
import com.example.feature.requests.FeatureDTO;
import com.example.feature.requests.FeatureUpdateRequest;
import com.example.feature.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    public final FeatureRepository featureRepository;

    @Override
    public void create(FeatureDTO request) {
        if (featureRepository.findByFullPath(request.getName()).isPresent()) {
            throw new ForbiddenExeption();
        }

        featureRepository.save(createProject(request));
    }

    @Override
    public List<FeatureDTO> read() {
        return  featureRepository.findByType(Type.PROJECT)
                .stream().map(this::createProjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void update(FeatureUpdateRequest request) {
        if (!request.getPath().equals("")){
            throw new ForbiddenExeption();
        }
        Feature project = featureRepository.findByFullPath(request.getFullPath())
                .orElseThrow(NotFoundExeption::new);

        project.setName(request.getName());
        project.setFullPath(project.getFullPath().replaceFirst(request.getFullPath(),
                request.getName()));

        featureRepository.save(project);

        featureRepository.findAll().stream().filter(feature ->
                feature.getFullPath().startsWith(request.getFullPath() + "/"))
                .forEach(feature -> updatePathAndFullPath(feature, request));
    }

    @Override
    public void delete(FeatureDTO request) {
        featureRepository.delete(featureRepository.findByFullPath(request.getName())
                .orElseThrow(NotFoundExeption::new));
    }

    private Feature createProject(FeatureDTO request){
        Feature project = new Feature();
        project.setName(request.getName());
        project.setPath("");
        project.setFullPath(request.getName());
        project.setType(Type.PROJECT);
        return project;
    }

    private FeatureDTO createProjectDTOWithoutChilds(Feature project){
        FeatureDTO projectDTO = new FeatureDTO();
        projectDTO.setName(project.getName());
        projectDTO.setPath("");
        return projectDTO;
    }

    private FeatureDTO createProjectDTO(Feature project){
        FeatureDTO projectDTO = createProjectDTOWithoutChilds(project);

        if (project.getChilds() != null)
            projectDTO.setFeatureList(project.getChilds().stream().filter(Objects::nonNull).map(this::createProjectDTOWithoutChilds)
                    .collect(Collectors.toList()));

        return projectDTO;
    }

    private void updatePathAndFullPath(Feature group, FeatureUpdateRequest request){
        group.setPath(group.getPath().replaceFirst(request.getFullPath(), request.getName()));

        group.setFullPath(group.getFullPath().replaceFirst(request.getFullPath(), request.getName()));
        featureRepository.save(group);
    }
}
