package com.ghada.authentificationjwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUser {
    private String username;
    private String password;
    private int space;
}
