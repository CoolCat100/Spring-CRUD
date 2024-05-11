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
    private Set<String> roles;
}
