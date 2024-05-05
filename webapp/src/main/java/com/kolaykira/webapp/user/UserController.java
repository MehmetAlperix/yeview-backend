package com.kolaykira.webapp.user;


import com.kolaykira.webapp.Config.TimestampConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.cloud.Timestamp;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("general/signup")
    public String signUp(@RequestBody UserRequest request) throws ExecutionException, InterruptedException
    {
        String name = request.getName();
        String surname = request.getSurname();
        String email = request.getEmail();
        String password = request.getPassword();
        Timestamp birthDate = TimestampConverter.convertJavaSqlTimestamp( request.getBirthDate()) ;
        String phone = request.getPhone();

        return userService.signUp(name,surname,email,password,birthDate,phone);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ChangePasswordRequest r) throws ExecutionException, InterruptedException {
        String email = r.getEmail();
        String newPassword = r.getNewPassword();
        boolean didChanged = userService.changePassword(email,newPassword);
        if (didChanged) {
            return ResponseEntity.ok("Change Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @GetMapping(path = "get/{userEmail}")
    public User getContractById(@PathVariable(name = "userEmail") String userEmail) throws ExecutionException, InterruptedException {
        return userService.getUserByEmail(userEmail);
    }
    @PutMapping("/{ownerEmail}")
    public String makeUserAnOwner(@PathVariable(name = "ownerEmail") String ownerEmail) throws ExecutionException, InterruptedException {
        return userService.makeOwner(ownerEmail);
    }

    @PutMapping("/makeNormaluser/{ownerEmail}")
    public String makeUserANormalUser(@PathVariable(name = "ownerEmail") String ownerEmail) throws ExecutionException, InterruptedException {
        return userService.makeNormalUser(ownerEmail);
    }
}