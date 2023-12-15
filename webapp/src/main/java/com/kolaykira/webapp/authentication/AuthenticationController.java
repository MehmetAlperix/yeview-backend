package com.kolaykira.webapp.authentication;

import com.kolaykira.webapp.user.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kolaykira.webapp.user.AuthenticationResponse;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/user/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticateService authenticateService;




    /*
    @GetMapping(path = "{userId}")
    public User getUserById(@PathVariable(name = "userId") int userId){
        return userService.getUser(userId);
    }
*/



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) throws ExecutionException, InterruptedException {
        String email = request.getEmail();
        String password = request.getPassword();
        AuthenticationResponse response = authenticateService.login(email, password);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else
        {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        }
    }






}
