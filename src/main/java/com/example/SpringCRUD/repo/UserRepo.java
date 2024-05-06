package com.example.SpringCRUD.repo;

import com.example.SpringCRUD.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collections;
import java.util.Map;

public interface UserRepo extends CrudRepository<User, Long> {
    public User findByLogin(String login);
    public User findByName(String name);
}
