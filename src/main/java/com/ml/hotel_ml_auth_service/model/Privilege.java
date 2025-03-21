package com.ml.hotel_ml_auth_service.model;

import com.ml.hotel_ml_auth_service.utils.converters.StringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "privileges")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"roles"})
@Table(name = "PRIVILEGES")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    @Convert(converter = StringConverter.class)
    private String name;

    @Column(name = "roles")
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

}