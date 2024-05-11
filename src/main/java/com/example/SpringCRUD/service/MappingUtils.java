package com.example.SpringCRUD.service;

import com.example.SpringCRUD.domain.Role;
import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MappingUtils {
    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        Set<Role> userRoles = user.getRoles();
        Set<String> roles = userRoles.stream().map(Role::getRole).collect(Collectors.toSet());
        userDTO.setRoles(roles);
        return userDTO;
    }
}
