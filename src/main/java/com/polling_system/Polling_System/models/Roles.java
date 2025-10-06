package com.polling_system.Polling_System.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import  org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements  GrantedAuthority{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length=20,unique = true)
    private ERole name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users=new HashSet<>();

    //injecting the class in the class itself
    public Roles(ERole name){
        this.name=name;
    }
    @Override
    public String getAuthority(){
        return name.name();
    }
    public enum ERole {
        ROLE_USER,
        ROLE_ADMIN
    }

}
