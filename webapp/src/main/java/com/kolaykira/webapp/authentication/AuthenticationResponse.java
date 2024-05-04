package com.kolaykira.webapp.authentication;

import com.kolaykira.webapp.user.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private Role role;
}
