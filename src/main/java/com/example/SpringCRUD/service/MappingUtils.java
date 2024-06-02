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
    private final TaxService taxService;

    public MappingUtils(TaxService taxService) {
        this.taxService = taxService;
    }

    public UserDTO mapToUserDTO(User user) {
        String[] roles = user.getRoles().stream().map(Role::getRole).toArray(String[]::new);
        return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getProfession(), user.getSalary(),
                taxService.countOldTax(user.getSalary()), taxService.countNewTax(user.getSalary()), user.getLogin(),
                user.getPassword(), roles);
    }
}
