package com.test.movierent.service.impl;

import com.test.movierent.dao.RoleDao;
import com.test.movierent.dao.UserDao;
import com.test.movierent.dao.VerificationTokenDao;
import com.test.movierent.exception.UserAlreadyExistException;
import com.test.movierent.model.User;
import com.test.movierent.model.VerificationToken;
import com.test.movierent.model.dto.UserDto;
import com.test.movierent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private VerificationTokenDao tokenDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    /** Override this method from UserDetailsService.class for use the users in our db */
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getAuthority(user)
        );
    }

    // Get a set of roles associated to the user
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            // Add prefix {ROLE_} to all roles
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User findOne(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User register(UserDto userDto) throws UserAlreadyExistException {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("Email already exist");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());

        // Default disable the user for work with the confirmation email
        user.setEnabled(false);

        // Add User role for default to the user
        //user.addRole(roleDao.findByName("USER"));
        return userDao.save(user);
    }

    @Override
    public void saveRegisteredUser(User user, VerificationToken verificationToken) {
        user.addRole(roleDao.findByName("USER"));
        userDao.save(user);
        tokenDao.save(verificationToken);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        // Type 1-Registration
        VerificationToken myToken = new VerificationToken(token, user, 1);
        tokenDao.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        // Type 1-Registration
        return tokenDao.findByTokenAndType(VerificationToken, 1);
    }

    @Override
    public void createRecoveryToken(User user, String token) {
        // Type 2-Recovery
        VerificationToken myToken = new VerificationToken(token, user, 2);
        tokenDao.save(myToken);
    }

    @Override
    public VerificationToken getRecoveryToken(String VerificationToken) {
        // Type 2-Recovery
        return tokenDao.findByTokenAndType(VerificationToken, 2);
    }

    @Override
    public void saveRecoveryToken(VerificationToken verificationToken, String newPassword) {
        User user = verificationToken.getUser();
        user.setPassword(bcryptEncoder.encode(newPassword));
        userDao.save(user);
        tokenDao.save(verificationToken);
    }

    @Override
    public User getUserFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.findOne(authentication.getName());
    }

    private boolean emailExist(String email) {
        return userDao.existsByEmail(email);
    }
}
