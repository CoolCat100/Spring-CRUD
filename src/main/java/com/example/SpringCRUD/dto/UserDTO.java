package com.example.SpringCRUD.dto;

import com.example.SpringCRUD.domain.Role;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private int age;
    private String profession;
    private int salary;
    private double oldTax;
    private double newTax;
    private String login; // уникальное значение
    private String password;
    private String[] roles;
}
