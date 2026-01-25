package com.hospitalcrud.dao.repositories;

import com.hospitalcrud.dao.model.Credential;

import java.util.List;
import java.util.Optional;

public interface CredentialRepository {
     List<Credential> getAll();

     Optional<Credential> findByUsername(String username);
}
