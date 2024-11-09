package com.ml.hotel_ml_auth_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Entity(name = "privileges")
@Getter
@Setter
@ToString(exclude = {"roles"})
@Table(name = "PRIVILEGES")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "roles")
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

}
