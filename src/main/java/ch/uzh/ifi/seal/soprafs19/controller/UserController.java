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

    @PutMapping("/users/{id}")
    ResponseEntity<User> updateUser(@RequestBody User newUser) {
        Long userId = newUser.getId();
        User user_found = this.userRepository.findById(Long.toString(userId));
        System.out.println(userId);
        String name = newUser.getUsername();
        System.out.println(name);

        if (!newUser.getUsername().equals(false)) {
            user_found.setUsername(newUser.getUsername());
        }

            if (!newUser.getName().equals(false)) {
                System.out.println("Hello!");
                user_found.setName(newUser.getName());
            }

                if (!newUser.getBirthday().equals(false)) {
                    user_found.setBirthday(newUser.getBirthday());
                }
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @PostMapping("/users")
    ResponseEntity<User> createUser(@RequestBody User newUser, HttpServletRequest request) {

        String requestType = request.getHeader("requestType");
        //  if the post is for register

        if (requestType.contains("register")) {

            try {
                User create_user = this.service.createUser(newUser);
                return new ResponseEntity<>(create_user, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null,null,HttpStatus.CONFLICT);
            }
        }else if(requestType.contains("login")){
            //  if the POST is for login
            try {
                String username = newUser.getUsername();
                User user_found = this.userRepository.findByUsername(username);
                if (user_found.getPassword().equals(newUser.getPassword())) {
                    return new ResponseEntity<>(user_found, HttpStatus.FOUND);
                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
                String token = newUser.getToken();
                User user_found = this.userRepository.findByToken(token);

                user_found.setUsername(newUser.getUsername());
                user_found.setBirthday(newUser.getBirthday());
                user_found.setName(newUser.getName());

                return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}



