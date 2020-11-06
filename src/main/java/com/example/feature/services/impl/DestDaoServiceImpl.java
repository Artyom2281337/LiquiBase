package com.example.feature.services.impl;

import com.example.feature.services.DestDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DestDaoServiceImpl implements DestDaoService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void insertDataWithProcedure(String... args) {
        jdbcTemplate.update("call origination_proxy.INSERT_DATA_INTO_DEST_SYSTEM_AND_DEST_ENDPOINT(?, 'ADDRESS', ?, '123/123')", args);
    }
}
