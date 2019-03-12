package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
public class UserController {

    private final UserService service;
    private  final UserRepository userRepository;

    UserController(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }



    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @PostMapping("/users")
        //  to check if the username is existed
    ResponseEntity<User> createUser(@RequestBody User newUser) {
        try {
            User create_user = this.service.createUser(newUser);
            return new ResponseEntity<>(create_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/users")
        //  to check if the username is existed
    ResponseEntity<User> user_login(@RequestBody User newUser) {
        try {
            String username = newUser.getUsername();
            User user_found = this.userRepository.findByUsername(username);
            if (user_found.getPassword() == newUser.getPassword()) {
                return new ResponseEntity<>(user_found, HttpStatus.FOUND);
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        }
}
