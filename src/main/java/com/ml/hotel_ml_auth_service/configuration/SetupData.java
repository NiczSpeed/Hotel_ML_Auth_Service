package com.ml.hotel_ml_auth_service.configuration;

import com.ml.hotel_ml_auth_service.dto.PrivilegeDto;
import com.ml.hotel_ml_auth_service.dto.RoleDto;
import com.ml.hotel_ml_auth_service.dto.UserDto;
import com.ml.hotel_ml_auth_service.mapper.PrivilegeMapper;
import com.ml.hotel_ml_auth_service.mapper.RoleMapper;
import com.ml.hotel_ml_auth_service.mapper.UserMapper;
import com.ml.hotel_ml_auth_service.repository.PrivilegeRepository;
import com.ml.hotel_ml_auth_service.repository.RoleRepository;
import com.ml.hotel_ml_auth_service.repository.UserRepository;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
public class SetupData implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public SetupData(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

    if (alreadySetup) return;

    PrivilegeDto readPrivilege = createPrivilegeIfNotExist("READ");
    PrivilegeDto writePrivilege = createPrivilegeIfNotExist("WRITE");

    List<PrivilegeDto> adminPrivileges = List.of(readPrivilege, writePrivilege);
    List<PrivilegeDto> userPrivileges = List.of(readPrivilege);

    createRoleIfNotExist("ADMIN", adminPrivileges);
    createRoleIfNotExist("USER", userPrivileges);

    RoleDto adminRole = RoleMapper.Instance.roleToRoleDto(roleRepository.findByName("ADMIN"));
    UserDto admin = new UserDto();
    admin.setFirstName("admin");
    admin.setLastName("admin");
    admin.setEmail("admin@admin.com");
    admin.setPassword("$2a$12$3ybnY6bwBe6lTsC/sQALV.1RDm/tzxGR4lTcGsK18C1B42yWsMqui");
    admin.setRoles(List.of(adminRole));
    userRepository.saveAndFlush(UserMapper.Instance.userDtoToUser(admin));

    alreadySetup = true;
    }

    @Transactional
    PrivilegeDto createPrivilegeIfNotExist(String privilegeName) {
        PrivilegeDto privilegeDto = PrivilegeMapper.Instance.privilegeToPrivilegeDto(privilegeRepository.findByName(privilegeName));
        if (privilegeDto == null) {
            privilegeDto = new PrivilegeDto(privilegeName,null);
            privilegeRepository.saveAndFlush(PrivilegeMapper.Instance.privilegeDtoToPrivilege(privilegeDto));
        }
        return privilegeDto;
    }

    @Transactional
    RoleDto createRoleIfNotExist(String roleName, Collection<PrivilegeDto> privileges) {
        RoleDto roleDto = RoleMapper.Instance.roleToRoleDto(roleRepository.findByName(roleName));
        if(roleDto == null) {
            roleDto = new RoleDto(roleName, null, privileges);
//            roleDto.setPrivileges(privileges);
            roleRepository.saveAndFlush(RoleMapper.Instance.roleDtoToRole(roleDto));
        }
        return roleDto;
    }

}
