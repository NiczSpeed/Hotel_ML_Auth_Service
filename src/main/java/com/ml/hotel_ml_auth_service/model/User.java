package com.ml.hotel_ml_auth_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "CreationDate")
    private LocalDate creationDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    (
        name = "USERS_ROLE",
        joinColumns = @JoinColumn(name = "user_uuid", referencedColumnName = "uuid"),
        inverseJoinColumns = @JoinColumn(name = "role_uuid", referencedColumnName = "uuid")
    )
    private Collection<Role> roles;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "isAccountNonExpired")
    private boolean isAccountNonExpired;
    @Column(name = "isEnabled")
    private boolean isEnabled;
}
