package com.test.movierent.service.impl;

import com.test.movierent.dao.RoleDao;
import com.test.movierent.model.Role;
import com.test.movierent.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public Role findById(Long roleId) {
        return roleDao.findById(roleId).orElse(null);
    }

    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role save(Role role) {
        return roleDao.save(role);
    }
}
