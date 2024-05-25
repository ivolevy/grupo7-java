package com.example.uade.tpo.controller;

import com.example.uade.tpo.entity.User;
import com.example.uade.tpo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/") //Get all users
    public List<User> getAllUsers() {
        return userService.getUsuarios();
    }

    @GetMapping("/{userId}") //Get user by id
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping //Create usuario
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.createUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}") //Update usuario
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            User updatedUser = userService.updateUser(userId, userDetails);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}") //Delete usuario
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        Boolean deleted = userService.deleteUser(userId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
