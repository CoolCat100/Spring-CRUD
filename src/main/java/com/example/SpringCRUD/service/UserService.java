package com.example.SpringCRUD.service;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }
    public void addUser(User user) {
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
}
