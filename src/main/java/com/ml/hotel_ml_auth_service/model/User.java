package com.ml.hotel_ml_auth_service.model;

import com.ml.hotel_ml_auth_service.utils.converters.LocalDateConverter;
import com.ml.hotel_ml_auth_service.utils.converters.StringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"roles"})
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "email")
    @Convert(converter = StringConverter.class)
    private String email;

    @Column(name = "password")
    @Convert(converter = StringConverter.class)
    private String password;

    @Column(name = "CreationDate")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate creationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    (
        name = "USERS_ROLE",
        joinColumns = @JoinColumn(name = "user_uuid"),
        inverseJoinColumns = @JoinColumn(name = "role_uuid")
    )
    private Set<Role> roles;

    @Column(name = "firstName")
    @Convert(converter = StringConverter.class)
    private String firstName;

    @Column(name = "lastName")
    @Convert(converter = StringConverter.class)
    private String lastName;

    @Column(name = "isAccountNonExpired")
    private boolean isAccountNonExpired;

    @Column(name = "isEnabled")
    private boolean isEnabled;
}
