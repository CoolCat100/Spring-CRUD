package com.example.SpringCRUD.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private String name;
    private int age;
    private String profession;
    public User(String name, int age, String profession) {
        this.name = name;
        this.age = age;
        this.profession = profession;
    }
}
