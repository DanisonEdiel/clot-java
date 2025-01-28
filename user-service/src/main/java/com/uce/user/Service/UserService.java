package com.uce.user.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uce.user.Mesage.UserMesageProducer;
import com.uce.user.Model.User;
import com.uce.user.Repository.UserRepository;
import com.uce.user.Service.ServiceImp.UserServiceImp;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMesageProducer userMesageProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(false);
        User savedUser = userRepository.save(user);
        userMesageProducer.sendMessage(savedUser);
        return savedUser;
    }

    @Override
    public User findByUser(ObjectId id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAll() {
       return userRepository.findAll();
    }

    

}
