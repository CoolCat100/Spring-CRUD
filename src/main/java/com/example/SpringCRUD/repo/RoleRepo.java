package com.example.SpringCRUD.repo;

import com.example.SpringCRUD.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {
}
