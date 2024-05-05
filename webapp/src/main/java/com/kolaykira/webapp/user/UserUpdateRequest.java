package com.kolaykira.webapp.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String mail;
    private String name;
    private String password;
    private String newPassword;
    private String surname;
    private String phone;
}
