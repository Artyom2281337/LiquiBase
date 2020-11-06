package com.example.feature.services.impl;

import com.example.feature.services.DestDaoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.CallableStatement;

@Service
@RequiredArgsConstructor
public class DestDaoServiceImpl implements DestDaoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insertDataWithProcedure(String name, String type) {
        final Session session = (Session) entityManager.getDelegate();
        session.doWork(connection -> {
            final CallableStatement callableStatement = connection.prepareCall("call origination_proxy.INSERT_DATA_INTO_DEST_SYSTEM_AND_DEST_ENDPOINT(?, ?, ?, ?)");
            callableStatement.setString(1, name);
            callableStatement.setString(2, "ADDRESS");
            callableStatement.setString(3, type);
            callableStatement.setString(4, "123/123");

            callableStatement.execute();
        });
    }
}
