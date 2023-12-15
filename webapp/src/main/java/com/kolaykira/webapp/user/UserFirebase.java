package com.kolaykira.webapp.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Lists;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firestore.v1.ArrayValue;
import jakarta.persistence.*;
import lombok.*;

import com.google.cloud.Timestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;



@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
//@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@Getter
@Setter
public class UserFirebase{
    @Id
    private String email;

    private String name;

    private String surname;

    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp birthDate;
    private String phone;
    private Boolean accountNonLocked;
    private Boolean loggedIn;
    private Boolean accountNonExpired;
    private Boolean enabled;
    private Boolean credentialsNonExpired;
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserFirebase(User user)
    {
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.loggedIn = user.getLoggedIn();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.surname = user.getSurname();
    }

/*
    public Timestamp getBirthDate() {
        return birthDate;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }


    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
*/
}