package com.example.SpringCRUD.service;

import com.example.SpringCRUD.domain.User;
import com.example.SpringCRUD.repos.UserRepo;
import lombok.AllArgsConstructor;
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
    public void addUser() {
        User user = new User("Alex", 18, "student");
        userRepo.save(user);
    }
}
