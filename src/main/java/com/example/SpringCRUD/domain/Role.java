package com.example.SpringCRUD.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
@Setter
@Getter
@Entity
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<User> users;
    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }
    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}
