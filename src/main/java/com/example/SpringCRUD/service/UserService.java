package com.example.SpringCRUD.service;

import com.example.SpringCRUD.config.SecurityConfig;
import com.example.SpringCRUD.domain.Role;
import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.dto.UserDTO;
import com.example.SpringCRUD.repo.UserRepo;
import dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final MappingUtils mappingUtils;
    private final RoleService roleService;

    public UserService(UserRepo userRepo, MappingUtils mappingUtils, RoleService roleService) {
        this.userRepo = userRepo;
        this.mappingUtils = mappingUtils;
        this.roleService = roleService;
        createAdminId();
    }

    private void createAdminId() {
        if (userRepo.findByLogin("Den") == null) {
            Role role = new Role(2L, "ROLE_ADMIN");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            User user = new User("Den", 25, "Admin", "Den", "123", roles);
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
            String pass = SecurityConfig.passwordEncoder().encode(user.getPassword());
            user.setPassword(pass);
            userRepo.save(user);
        }
    }

    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }

    public boolean addUser(User user, String role) {
        if (getUsers().stream().anyMatch(users -> user.getLogin().equals(users.getLogin()))) {
            System.out.println("false");
            return false;
        }
        if (role.equals("user")) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        if (role.equals("admin")) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_Admin")));
        }
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
    public void updateUser(UserDTO user) {
        User oldUser = userRepo.findByLogin(user.getLogin());
        Set<Role> roles = new HashSet<>();
        for (String role : user.getRoles()) {
            if (role.equals("user")) {
                roles.add(new Role(1L, "ROLE_USER"));
            }
            if (role.equals("admin")) {
                roles.add(new Role(2L, "ROLE_ADMIN"));
            }
        }
        oldUser.setRoles(roles);
        oldUser.setName(user.getName());
        oldUser.setAge(user.getAge());
        oldUser.setProfession(user.getProfession());
        if (user.getPassword() != null) {
            String newPass = SecurityConfig.passwordEncoder().encode(user.getPassword());
            oldUser.setPassword(newPass);
        }
        userRepo.save(oldUser);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
    public void deleteUser(UserDTO user) {
        userRepo.deleteById(userRepo.findByLogin(user.getLogin()).getId());
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

//    public List<String> getUserRolesDTO(User user) {
//        UserDTO userDTO = mappingUtils.mapToUserDTO(user);
//        return new ArrayList<>(userDTO.getRoles());
//    }

    public boolean addUserDto(UserDTO user) {
        User newUser = new User(user.getName(), user.getAge(), user.getProfession(), user.getLogin(), null, null);

        if (getUsers().stream().anyMatch(users -> user.getLogin().equals(users.getLogin()))) {
            System.out.println("false");
            return false;
        }
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() == null) {
            roles.add(new Role(1L, "ROLE_USER"));
        } else {
            for (String role : user.getRoles()) {
                if (role.equals("user")) {
                    roles.add(new Role(1L, "ROLE_USER"));
                }
                if (role.equals("admin")) {
                    roles.add(new Role(2L, "ROLE_ADMIN"));
                }
            }
        }
        newUser.setRoles(roles);
        String pass = SecurityConfig.passwordEncoder().encode(user.getPassword());
        newUser.setPassword(pass);
        userRepo.save(newUser);
        System.out.println("true");
        return true;
    }
}
