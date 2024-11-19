package com.hospitalcrud.dao.repositories.spring;

import com.hospitalcrud.dao.mappers.CredentialRowMapperSpring;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("springJdbc")
@Repository
public class CredentialRepositoryImpl implements CredentialRepository {

    @Autowired
    private JdbcClient  jdbcClient;
    private final CredentialRowMapperSpring rowMapper;

    public CredentialRepositoryImpl(CredentialRowMapperSpring rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Credential> getAll() {
        return jdbcClient.sql("select  *  from user_login")
                .query(rowMapper) //TODO aquí no se si debería de poner Credential.class
                .list();
    }

    public Credential get(String username) {
        return jdbcClient.sql("select * from user_login where username = ?")
                .param(1, username)
                .query(rowMapper)
                .single();
    }
}
