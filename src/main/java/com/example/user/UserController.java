package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private InterestService interestService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/interests")
    public List<Interest> getUserInterests(@PathVariable int id){
        return userService.getUserById(id).getInterests();
    }

    @PostMapping("/{userId}/interest/{interestId}")
    public User addUserInterest(@PathVariable int userId, @PathVariable int interestId){
        User user = userService.getUserById(userId);
        Interest interest = interestService.getInterestById(interestId);

        user.getInterests().add(interest);
        userService.saveUser(user);

        return user;
    }

    @DeleteMapping("/{userId}/interest/{interestId}")
    public User deleteUserInterest(@PathVariable int userId, @PathVariable int interestId) {
        User user = userService.getUserById(userId);
        Interest interest = interestService.getInterestById(interestId);

        user.getInterests().remove(interest);
        userService.saveUser(user);

        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
