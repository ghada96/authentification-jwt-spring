package com.ghada.authentificationjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    private AuthToken token;
    private UserDTO user;
    private String expiresIn;
}
