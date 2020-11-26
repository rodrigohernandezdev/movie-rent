package com.example.movierent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq", initialValue = 10)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // Add JsonIgnore for not return in the response for security
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "first_name", length = 50)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", length = 50)
    @JsonProperty("last_name")
    private String lastName;

    @Column
    private Integer age;

    private Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = {
                @JoinColumn(name = "user_id") }, inverseJoinColumns = {
                @JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})})
    private List<Role> roles;


    /**
     * @param role for add a new role to the user, it is verified if the role already in the user.
     **/
    public void addRole(Role role){
        if (roles==null){
            roles = new ArrayList<>();
        }
        Role found = roles.stream().filter(role1 -> role1.getId().equals(role.getId())).findFirst().orElse(null);
        if (found == null){
            this.roles.add(role);
        }
    }

}
