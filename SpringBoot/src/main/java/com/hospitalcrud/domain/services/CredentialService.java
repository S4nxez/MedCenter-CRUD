package com.hospitalcrud.domain.services;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repositories.CredentialRepository;
import com.hospitalcrud.domain.model.CredentialUI;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CredentialService {

    private CredentialRepository credentialRepository;

    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public boolean get(CredentialUI credentialui) {

        Credential credentialRepository = this.credentialRepository.get(credentialui.getUsername());

        boolean aux;
        if (Objects.equals(credentialRepository.getPassword(), credentialui.getPassword())) {
            aux = true;
        } else aux = false;

        return aux;
    }
}
