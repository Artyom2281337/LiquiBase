package com.example.feature

import com.example.feature.entity.Feature
import com.example.feature.enums.Type
import com.example.feature.repositories.FeatureRepository
import com.example.feature.requests.FeatureDTO
import com.example.feature.requests.FeatureUpdateRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import javax.transaction.Transactional

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class FeatureApplicationTests {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private FeatureRepository featureRepository

    private static final String CREATE_PROJECT = "/project/create"
    private static final String DELETE_PROJECT = "/project/delete"
    private static final String GET_PROJECTS = "/project/all"
    private static final String UPDATE_PROJECT = "/project/update"

    @Test
    @Transactional
    void craateProject(){
        given:
        def request = new FeatureDTO()
        request.name = "proj1"
        request.path = ""

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post(CREATE_PROJECT)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(status().isOk())

        def project = featureRepository.findAll()

        assertEquals(request.name, project.get(0).name)
        assertEquals(request.path, project.get(0).path)
    }

    @Test
    @Transactional
    void deleteProject(){
        given:
        def request = new FeatureDTO()
        request.name = "proj1"
        request.path = ""

        def project = new Feature()
        project.setName(request.name)
        project.setPath(request.path)
        project.setType(Type.PROJECT)
        project.setFullPath(request.name)
        featureRepository.save(project)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_PROJECT)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(status().isOk())

        assertEquals(true, featureRepository.findAll().empty)
    }

    @Test
    @Transactional
    void getProjects(){
        List<Feature> projectList = []

        for (int i = 0; i < 3; i++) {
            Feature project = new Feature()
            project.name = "proj" + i
            project.path = ""
            project.fullPath = project.name
            project.type = Type.PROJECT
            project.childs = null
            projectList.add(project)
        }

        featureRepository.saveAll(projectList)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(GET_PROJECTS)
                .contentType(MediaType.APPLICATION_JSON))

        then:
        response.andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(3)))
                .andExpect(jsonPath('$.[0].name').value("proj0"))
                .andExpect(jsonPath('$.[2].path').value(""))
                .andExpect(jsonPath('$.[2].featureList').value(null))
    }

    @Test
    @Transactional
    void updateProject(){
        given:
        FeatureUpdateRequest request = new FeatureUpdateRequest()

        request.name = "proj1upd"
        request.path = ""
        request.fullPath = "proj1"

        Feature project = new Feature()

        project.name = "proj1"
        project.path = ""
        project.type = Type.PROJECT
        project.fullPath = "proj1"
        featureRepository.save(project)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_PROJECT)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        then:
        response.andExpect(status().isOk())

        def feature = featureRepository.findAll().get(0)
        assertEquals(request.name, feature.name)
        assertEquals(request.name, feature.fullPath)
    }
}
