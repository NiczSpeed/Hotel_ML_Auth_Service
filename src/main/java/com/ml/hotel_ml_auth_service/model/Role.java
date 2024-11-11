package com.ml.hotel_ml_auth_service.model;

import com.ml.hotel_ml_auth_service.utils.converters.StringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "roles")
@Data
@Builder
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
    @Convert(converter = StringConverter.class)
    private String name;

    @Column(name = "users")
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Column(name = "privileges")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    (
        name = "ROLES_PRIVILEGES",
        joinColumns = @JoinColumn(name = "role_uuid"),
        inverseJoinColumns = @JoinColumn(name = "privilege_uuid")
    )
    private Set<Privilege> privileges;
}
