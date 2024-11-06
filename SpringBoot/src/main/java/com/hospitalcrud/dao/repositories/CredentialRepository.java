package com.hospitalcrud.dao.repositories;

import com.hospitalcrud.dao.model.Credential;

public interface CredentialRepository {
     Credential get(String username);
}
