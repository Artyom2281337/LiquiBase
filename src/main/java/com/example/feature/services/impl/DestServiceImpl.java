package com.example.feature.services.impl;

import com.example.feature.entity.DestEndpoint;
import com.example.feature.entity.DestSystem;
import com.example.feature.repositories.DestEndpointRepository;
import com.example.feature.repositories.DestSystemRepository;
import com.example.feature.services.DestDaoService;
import com.example.feature.services.DestService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DestServiceImpl implements DestService {

    private final DestSystemRepository destSystemRepository;
    private final DestEndpointRepository destEndpointRepository;

    private final DestDaoService destDaoService;

    @Override
    @Transactional
    public void insertData(String... args) {
        destDaoService.insertDataWithProcedure(args);
        List<DestSystem> destSystems = destSystemRepository.findAll();
        List<DestEndpoint> destEndpoints = destEndpointRepository.findAll();
        int a = 0;
    }
}
