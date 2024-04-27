package com.example.SpringCRUD.repos;

import com.example.SpringCRUD.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
