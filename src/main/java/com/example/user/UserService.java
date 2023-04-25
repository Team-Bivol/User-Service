package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public User createUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        User newUser = new User(user.getUsername(), user.getEmail(), password);
        userRepository.save(newUser);
        return newUser;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public String Login(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(userRequest.getPassword(), user.get().getPassword())) {

                return user.get().getId();
            } else {
            throw new IllegalArgumentException("Wrong password.");
            }
        } else {
         throw new IllegalArgumentException("Wrong username");
        }
    }
}
