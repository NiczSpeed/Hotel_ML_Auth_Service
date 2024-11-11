package com.ml.hotel_ml_auth_service.service;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.exception.ErrorWhileEncodeException;
import com.ml.hotel_ml_auth_service.exception.ErrorWhileSavePrivileges;
import com.ml.hotel_ml_auth_service.exception.ErrorWhileSaveRoles;
import com.ml.hotel_ml_auth_service.exception.ErrorWhileSaveUser;
import com.ml.hotel_ml_auth_service.mapper.PrivilegeMapper;
import com.ml.hotel_ml_auth_service.model.Privilege;
import com.ml.hotel_ml_auth_service.model.Role;
import com.ml.hotel_ml_auth_service.model.User;
import com.ml.hotel_ml_auth_service.repository.PrivilegeRepository;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;
import com.ml.hotel_ml_auth_service.utils.Encryptor;
import com.ml.hotel_ml_auth_service.utils.EncryptorUtil;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EncryptorService {

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final UserService userService;

    Logger logger = Logger.getLogger(getClass().getName());

    @PostConstruct
    private void encryptDataInsideDatabase() {
        try {
            initPrivileges();
            initRoles();
            initUsers();
        } catch (ErrorWhileSavePrivileges | ErrorWhileSaveRoles | ErrorWhileSaveUser e) {
            logger.warning(e.getMessage());
        }


    }

    private void initPrivileges() {
        if (privilegeRepository.count() == 0) {
            Privilege readPrivilege = Privilege.builder()
                    .name("READ")
                    .build();
            Privilege writePrivilege = Privilege.builder()
                    .name("WRITE")
                    .build();
            privilegeRepository.saveAll(List.of(readPrivilege, writePrivilege));
        }
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            Role userRole = Role.builder()
                    .name("USER")
                    .privileges(Set.of(privilegeService.findByName("READ")))
                    .build();
            Role adminRole = Role.builder()
                    .name("ADMIN")
                    .privileges(Set.of(privilegeService.findByName("READ"), privilegeService.findByName("WRITE")))
                    .build();
            roleRepository.saveAll(List.of(userRole, adminRole));
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .email("admin@admin.com")
                    .creationDate(LocalDate.now())
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .firstName("admin")
                    .lastName("admin")
                    .password("niczspeed")
                    .roles(Set.of(roleService.findRoleByName("ADMIN")))
                    .build();
            userRepository.save(admin);
        }
    }

//    private<T> boolean checkIfExist (T object){
//
//    }

}
