package com.kolaykira.webapp.authentication;

import com.kolaykira.webapp.authentication.jwt.JwtService;
import com.kolaykira.webapp.user.AuthenticationResponse;
import com.kolaykira.webapp.user.User;
import com.kolaykira.webapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AuthenticateService {


    private final PasswordEncoder passwordEncoder;



    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationResponse login(String email, String password ) throws ExecutionException, InterruptedException
    {

        User u = userService.getUserByEmail(email);
        if( u == null)
        {
            return null;
        }
        else
        {
            boolean matches = passwordEncoder.matches(password, u.getPassword());
            if(matches)
            {
                u.setLoggedIn(true);

                authenticate(email,password);
                String token = generateToken(u);
                return AuthenticationResponse.builder().token(token).build();
            }
            else
            {
                return null;
            }
        }
    }
    public boolean authenticate(String email, String password)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (AuthenticationException e) {
            System.out.println(e.getMessage());

        }

        return true;
    }

    public String generateToken(User u )
    {
        return jwtService.generateToken(u);
    }
}
