package com.ml.hotel_ml_auth_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"users", "privileges"})
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
    private Set<User> users;

    @Column(name = "privileges")
    @ManyToMany(fetch = FetchType.EAGER)
//    @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
    @JoinTable
    (
        name = "ROLES_PRIVILEGES",
        joinColumns = @JoinColumn(name = "role_uuid"),
        inverseJoinColumns = @JoinColumn(name = "privilege_uuid")
    )
    private Set<Privilege> privileges;
}
