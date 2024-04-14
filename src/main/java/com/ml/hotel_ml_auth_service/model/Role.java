package com.ml.hotel_ml_auth_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "users")
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @Column(name = "privileges")
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable
    (
        name = "ROLES_PRIVILEGES",
        joinColumns = @JoinColumn(name = "role_uuid", referencedColumnName = "uuid"),
        inverseJoinColumns = @JoinColumn(name = "privilege_uuid", referencedColumnName = "uuid")
    )
    private Collection<Privilege> privileges;
}
