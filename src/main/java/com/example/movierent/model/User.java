package com.example.movierent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_ROLES",
            joinColumns = {
                @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
                @JoinColumn(name = "ROLE_ID")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","ROLE_ID"})})
    private List<Role> roles;

    public void addRole(Role role){
        if (roles==null){
            roles = new ArrayList<>();
        }
        Role finded = roles.stream().filter(role1 -> role.getId() == role1.getId()).findFirst().orElse(null);
        if (finded == null){
            this.roles.add(role);
        }
    }

}
