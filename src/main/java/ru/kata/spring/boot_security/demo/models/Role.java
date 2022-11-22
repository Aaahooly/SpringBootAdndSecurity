package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_role")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new TreeSet<>();

    public Role(int id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }


    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserRole() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getUserRole();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", userRole='" + name + '\'' +
                '}';
    }
}
