package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@RestController
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;

    UserController(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }


    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @PutMapping("/users/{userId}")
    @CrossOrigin
    ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User newUser) {
        return service.updateUser(userId, newUser);
    }


    @PostMapping("/users")
    ResponseEntity<User> userEntry(@RequestBody User newUser, HttpServletRequest request) {
                return service.userEntry(newUser, request);
    }
}



