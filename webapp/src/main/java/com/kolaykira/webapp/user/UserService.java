package com.kolaykira.webapp.user;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.kolaykira.webapp.exception.InvalidCredentialsException;
import com.kolaykira.webapp.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final String COLLECTION_NAME = "user";


    @Autowired
    private  PasswordEncoder passwordEncoder;


    public String signUp(String name, String surname, String email, String password, Timestamp birthDate, String phone)
            throws ExecutionException, InterruptedException
    {
        // Check if email is already registered
        /*
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
         */

        // Create a new User instance
        User newUser = new User(name, surname, email, passwordEncoder.encode(password), birthDate, phone, false, false);
        //User newUser = new User(name, surname, email, password, birthDate, phone, false, false);

        // Save the new user to the database

        UserFirebase userFirebase = new UserFirebase( newUser );
        return saveUserToFirebase(userFirebase);


    }


    public User getUserByEmail( String email) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COLLECTION_NAME).document(email);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if(document.exists() )
        {
            User user = new User( document.toObject(UserFirebase.class) );
            return user;

        }
        else
        {
            return null;
        }
    }





    /**
     * This is the main method which makes use of addNum method.
     * @param email the email to be change
     * @return true if it is changed, false if it is not.
    */
    public boolean changePassword(String email, String newPassword) throws ExecutionException, InterruptedException
    {
        User u = getUserByEmail(email);
        if(u == null)
        {
            return false;
        }
        u.setPassword( passwordEncoder.encode( newPassword) );
        saveUserToFirebase(new UserFirebase(u) );
        return true;
    }

    public String saveUserToFirebase(UserFirebase u) throws ExecutionException, InterruptedException
    {

        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(u.getEmail()).set(u);
        return collectionAPIFuture.get().getUpdateTime().toString();
    }

    public String makeOwner(String ownerEmail) throws ExecutionException, InterruptedException {
        User u = getUserByEmail(ownerEmail);
        u.setRole(Role.OWNER);
        saveUserToFirebase(new UserFirebase(u));
        return "Role Changed Successfully";
    }

    public String makeNormalUser(String ownerEmail) throws ExecutionException, InterruptedException {
        User u = getUserByEmail(ownerEmail);
        u.setRole(Role.NormalUser);
        saveUserToFirebase(new UserFirebase(u));
        return "Role Changed Successfully";
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmail(username);
    }

    public boolean changeUserDetails(String email, String password, String newPassword,String name, String surname,String phoneNumber) throws ExecutionException, InterruptedException
    {
        User u = getUserByEmail(email);
        if(u == null)
        {
            throw new NoDataFoundException("No username");
        }


        if( passwordEncoder.matches(password, u.getPassword()) )
        {
            u.setPassword( passwordEncoder.encode( newPassword) );
            u.setName(name);
            u.setSurname(surname);
            u.setPhone(phoneNumber);
        }
        else
        {
            throw new InvalidCredentialsException("Wrong password");
        }


        saveUserToFirebase(new UserFirebase(u) );
        return true;
    }


}

