package com.example.SpringCRUD.service;

import com.example.SpringCRUD.config.SecurityConfig;
import com.example.SpringCRUD.domain.Role;
import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.dto.UserDTO;
import com.example.SpringCRUD.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final MappingUtils mappingUtils;
    private final RoleService roleService;

    public UserService(UserRepo userRepo, MappingUtils mappingUtils, RoleService roleService) {
        this.userRepo = userRepo;
        this.mappingUtils = mappingUtils;
        this.roleService = roleService;
    }

    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }

    public boolean addUser(User user) {
        if (getUsers().stream().anyMatch(users -> user.getLogin().equals(users.getLogin()))) {
            System.out.println("false");
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        String pass = SecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(pass);
        userRepo.save(user);
        System.out.println("true");
        return true;
    }

    public User getUser(long id) {
        return userRepo.findById(id).orElse(null);
    }

    public void updateUser(long id, User user, String pass) {
        User oldUser = userRepo.findById(id).orElse(null);
        oldUser.setName(user.getName());
        oldUser.setAge(user.getAge());
        oldUser.setProfession(user.getProfession());
        if (pass != null) {
            String newPass = SecurityConfig.passwordEncoder().encode(pass);
            oldUser.setPassword(newPass);
        }
        userRepo.save(oldUser);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    public User getUserByName(String name) {
        return userRepo.findByLogin(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(name);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public User getCurrentUser() {
        return userRepo.findByLogin(getCurrentUsername());
    }

    public Set<Role> getRoles(User user) {
        return user.getRoles();
    }
    public boolean isRoleUser(int id) {
        long roleUserId = 1;
        User user = getUser(id);
        Set<Role> roles = user.getRoles();
        return roles.contains(roleService.getRoleById(roleUserId));
    }
    public void checkRoleUser(long id, String isTheUser) {
        long roleId = 1;
        if (isTheUser.equals("on")) {
            addCurrentRole(id, roleId);
        } else {
            deleteCurrentRole(id, roleId);
        }
    }
    public boolean isAdmin(long id) {
        long roleId = 2;
        User user = getUser(id);
        Set<Role> roles = user.getRoles();
        return roles.contains(roleService.getRoleById(roleId));
    }
    public void checkRoleAdmin(long id, String isTheAdmin) {
        long roleId = 2;
        if (isTheAdmin.equals("on")) {
            addCurrentRole(id, roleId);
        } else {
            deleteCurrentRole(id, roleId);
        }
    }

    public void addCurrentRole(long id, long roleId) {
        User userEdit = getUser(id);
        Set<Role> roles = userEdit.getRoles();
        if (!roles.contains(roleService.getRoleById(roleId))) {
            roles.add(roleService.getRoleById(roleId));
            updateUser(userEdit.getId(), userEdit, null);
        }
    }

    public void deleteCurrentRole(long id, long roleId) {
        User userEdit = getUser(id);
        Set<Role> roles = userEdit.getRoles();
        if (roles.contains(roleService.getRoleById(roleId))) {
            roles.remove(roleService.getRoleById(roleId));
            updateUser(userEdit.getId(), userEdit, null);
        }
    }
    public List<String> getUserRolesDTO(User user) {
        UserDTO userDTO = mappingUtils.mapToUserDTO(user);
        return new ArrayList<>(userDTO.getRoles());
    }
}
