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


@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
//@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)

public class User implements UserDetails {
    @Id
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp birthDate;

    @Getter
    @Setter
    private String phone;

    @Setter
    private Boolean accountNonLocked;
    @Getter
    @Setter
    private Boolean loggedIn;

    @Setter
    private Boolean accountNonExpired;

    @Setter
    private Boolean enabled;

    @Setter
    private Boolean credentialsNonExpired;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;



    public User(String name, String surname, String email, String password, Timestamp birthDate, String phone, Boolean accountNonLocked, Boolean loggedIn) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.phone = phone;
        this.accountNonLocked = !accountNonLocked;
        this.loggedIn = loggedIn;
        role = Role.NormalUser;
        this.accountNonExpired = true;
        this.enabled = true;
        this.credentialsNonExpired = true;
    }

    public User (UserFirebase user)
    {
        this.accountNonExpired = user.getAccountNonExpired();
        this.accountNonLocked = user.getAccountNonLocked();
        this.credentialsNonExpired = user.getCredentialsNonExpired();
        this.role = user.getRole();
        this.enabled = user.getEnabled();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.loggedIn = user.getLoggedIn();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.surname = user.getSurname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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