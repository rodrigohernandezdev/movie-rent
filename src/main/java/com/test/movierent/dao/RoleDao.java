package com.test.movierent.dao;

import com.test.movierent.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "roleDao")
public interface RoleDao extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
