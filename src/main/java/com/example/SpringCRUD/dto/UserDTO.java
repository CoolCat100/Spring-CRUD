package com.example.SpringCRUD.dto;

import com.example.SpringCRUD.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
    private String name;
    private int age;
    private String profession;
    private String login; // уникальное значение
    private String password;
    private String[] roles;
}
