package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> findAll(){
        return roleRepository.findAll().stream().collect(Collectors.toSet());
    }


    public User setUserRole(User user, String[] roleName) {
        for (Role role : findAll()) {
            for (int i = 0; i < roleName.length; i++) {
                if (role.getName().equals(roleName[i])) {
                    user.getRoles().add(role);
                }
            }
        }
        return user;
    }
}
