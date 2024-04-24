package com.kolaykira.webapp.user;


import com.kolaykira.webapp.Config.TimestampConverter;
import com.kolaykira.webapp.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.cloud.Timestamp;;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
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
    @GetMapping(path = "{userEmail}")
    public User getContractById(@PathVariable(name = "userEmail") String userEmail) throws ExecutionException, InterruptedException {
        return userService.getUserByEmail(userEmail);
    }
/*
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session){

        // Perform authentication logic here, such as validating username and password
        if (userService.getUserByEmail(email) != null && userService.passwordMatches(password,
                userService.getUserByEmail(email).getPassword())
        ) {
            // Authentication successful, set user information in session
            session.setAttribute("userId", userService.getUserByEmail(email).getId());
            if(userService.getUserByEmail(email).getRole() == Role.ADMIN){ return "redirect:/admin_dashboard";}
            else{return  "redirect:/home";}
        } else {
            // Authentication failed, redirect back to login page with an error message
            return "redirect:/login";
        }
    }



    @PostMapping("/signup")
    public String signup(@RequestParam("name") String name,
                         @RequestParam("surname") String surname,
                         @RequestParam("birthdate") String birthdate,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("city") String city,
                         HttpSession session){

        if (userService.getUserByEmail(email) != null) {
            // User with the same email already exists, redirect back to signup page with an error message
            return "redirect:/signup?error=emailExists";
        }

        // Create a new appUserRequest
        AppUserRequest appUserRequest = new AppUserRequest(name, surname, email, password, Timestamp.valueOf(birthdate), 0.0, city);

        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setBirthDate(Timestamp.valueOf(birthdate));
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(Role.APP_USER);

        // Save the new user to the database
        //userService.saveUser(newUser);
        appUserService.addAppUser(appUserRequest);
        // Set user information in session
        session.setAttribute("userId", newUser.getId());

        // Redirect to the home page or any other desired destination
        return "redirect:/home";
    }
    */

}