package com.hospitalcrud.dao.model;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class LoginResponse {
    private final String token;
    private final String refreshToken;
}
