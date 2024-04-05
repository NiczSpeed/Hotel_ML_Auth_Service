package com.ml.hotel_ml_auth_service.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Table(name = "PRIVILEGE")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "roles")
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}
