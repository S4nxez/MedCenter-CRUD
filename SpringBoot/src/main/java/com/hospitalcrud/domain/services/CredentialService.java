package com.hospitalcrud.domain.services;

import com.hospitalcrud.dao.model.LoginResponse;
import com.hospitalcrud.dao.repositories.CredentialRepository;
import com.hospitalcrud.domain.model.CredentialUI;
import com.hospitalcrud.utils.PasswordHash;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequiredArgsConstructor
@Service
public class CredentialService {

    private final CredentialRepository credentialRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordHash passwordHash;
    private final JwtService jwtService;

    public LoginResponse login(CredentialUI credentialui) {
        boolean isAuthenticated = credentialRepository.findByUsername(credentialui.getUsername())
                .map(c -> {
                            try {
                                return passwordHash.validatePassword(credentialui.getPassword(), c.getPassword());
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).orElse(false);

        if (isAuthenticated) {
            String token = jwtService.generateToken(userDetailsService.loadUserByUsername(credentialui.getUsername()));
            String refreshToken = jwtService.generateRefreshToken(userDetailsService.loadUserByUsername(credentialui.getUsername()));
            return new LoginResponse(
                    token,
                    refreshToken
            );
        } else {
            return null;
        }
    }
}
