package com.hospitalcrud.ui;

import com.hospitalcrud.dao.model.LoginResponse;
import com.hospitalcrud.domain.model.CredentialUI;
import com.hospitalcrud.domain.services.CredentialService;
import com.hospitalcrud.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestCredentials {

    private final CredentialService credentialService;

    @PostMapping(Constants.LOGIN_PATH)
    public ResponseEntity<LoginResponse> login(@RequestBody CredentialUI credentialui){
        LoginResponse tokens = credentialService.login(credentialui);
        return tokens == null
                ? ResponseEntity.status(401).body(null)
                : ResponseEntity.ok(tokens);
    }
}
