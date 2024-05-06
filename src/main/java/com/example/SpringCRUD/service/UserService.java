package com.example.SpringCRUD.service;

import com.example.SpringCRUD.domain.Role;
import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.repo.RoleRepo;
import com.example.SpringCRUD.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public Role getRoleAdmin() {
        return roleRepo.findById(2L).get();
    }

    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }

    public void addUser(User user) {
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userRepo.save(user);
    }

    public User getUser(long id) {
        return userRepo.findById(id).orElse(null);
    }

    public void updateUser(long id, User user) {
        User oldUser = userRepo.findById(id).orElse(null);
        oldUser.setName(user.getName());
        oldUser.setAge(user.getAge());
        oldUser.setProfession(user.getProfession());
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
        System.out.println(auth.getName());
        return auth.getName();
    }
    public User getCurrentUser() {
        return userRepo.findByLogin(getCurrentUsername());
    }
    public Set<Role> getRoles(User user) {
        return user.getRoles();
    }
    public void changeRole(long id) {
        User userEdit = getUser(id);
        Set<Role> roles = userEdit.getRoles();
        if (roles.contains(getRoleAdmin())) {
            roles.remove(getRoleAdmin());
        } else {
            roles.add(getRoleAdmin());
        }
        updateUser(userEdit.getId(), userEdit);
    }
}
