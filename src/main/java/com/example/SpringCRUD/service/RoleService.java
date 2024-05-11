package com.example.SpringCRUD.service;

import com.example.SpringCRUD.domain.Role;
import com.example.SpringCRUD.repo.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    public List<String> getRolesFromDB() {
        List<Role> roles = (List<Role>) roleRepo.findAll();
        return roles.stream().map(Role::getRole).collect(Collectors.toList());
    }
    public Role getRoleById(long id) {
        return roleRepo.findById(id).orElse(null);
    }
}
