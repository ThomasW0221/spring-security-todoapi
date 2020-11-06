package io.twinterf.todowithjwtauth.service;

import io.twinterf.todowithjwtauth.models.UserDao;
import io.twinterf.todowithjwtauth.models.UserDto;
import io.twinterf.todowithjwtauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TodoUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public TodoUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDao user = userRepository.findById(s).orElseThrow(() -> new UsernameNotFoundException("User not found: " + s));
        return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }

    public UserDto saveUser(UserDto newUser) {
        var userDao = new UserDao();
        userDao.setUserName(newUser.getUsername());
        userDao.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(userDao);
        newUser.setPassword("its a secret");
        return newUser;
    }
}
