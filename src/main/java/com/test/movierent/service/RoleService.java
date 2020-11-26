package com.test.movierent.service;

import com.test.movierent.model.Role;

import java.util.List;

public interface RoleService {
    Role findById(Long roleId);
    Role findByName(String roleName);
    List<Role> findAll();
    Role save(Role role);
}
