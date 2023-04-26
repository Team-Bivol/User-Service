package com.example.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private InterestService interestService;

    @Autowired
    HttpSession session;

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest userRequest) {
        try {
            String id = userService.Login(userRequest);
            if (id != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Set-Cookie", id );
                session.setAttribute("header",headers.get("Set-Cookie").get(0));
                session.setAttribute("userEmail", userService.getUserById(id).getEmail());
                session.setAttribute("userInterests", userService.getUserById(id));

                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), e.getMessage());
        }
    }

    @GetMapping("/interest")
    public List<Interest> getUserInterests(){
        return userService.getUserById(session.getAttribute("header").toString()).getInterests();
    }

    @GetMapping("/email")
    public String getEmail(){
        return session.getAttribute("userEmail").toString();

    }
    @PostMapping("/interest")
    public User addUserInterest(@RequestBody UserInterestRequest userInterestRequest) {
        User user = userService.getUserById(userInterestRequest.getUserId());
        Interest interest = interestService.getInterestById(userInterestRequest.getInterestId());

        List<Interest> interests = user.getInterests();
        interests.add(interest);
        user.setInterests(interests);

        userService.createUser(user);

        return user;
    }

}
