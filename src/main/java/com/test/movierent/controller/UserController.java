package com.test.movierent.controller;

import com.test.movierent.exception.NotExistException;
import com.test.movierent.model.Role;
import com.test.movierent.model.User;
import com.test.movierent.service.RoleService;
import com.test.movierent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> findAll() {
        List<User> userList = userService.findAll();
        if (userList == null || userList.size() == 0){
            throw new NotExistException("No users are available");
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> findById(@PathVariable(name = "userId") Long userId ) {
        User userDetail = userService.findById(userId);
        if (userDetail == null ){
            throw new NotExistException("User: "+ userId + " was not found");
        }
        return new ResponseEntity<>(userDetail, HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateRoleById(@PathVariable(name = "userId") Long userId, @RequestParam() String roleName) {
        User user = userService.findById(userId);
        if (user == null ){
            throw new NotExistException("User: "+ userId + " was not found");
        }
        Role role = roleService.findByName(roleName);
        if (role == null ){
            throw new NotExistException("Role: "+ roleName + " was not found");
        }
        user.addRole(role);

        userService.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
