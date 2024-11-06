package com.hospitalcrud.dao.repositories.staticDAO;

import com.hospitalcrud.dao.model.Credential;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("txtFiles")
public class StaticCredentialRepository implements com.hospitalcrud.dao.repositories.CredentialRepository {

    private List<Credential> listCredentials;

    public void add(Credential credential) {
        //TODO add creadential
    }

    public Credential get(String username) {

        return new Credential("root","root", 1);
    }


}
