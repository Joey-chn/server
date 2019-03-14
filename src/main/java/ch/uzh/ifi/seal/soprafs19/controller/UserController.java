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

//    ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User newUser) {
//
//        User user_update = this.userRepository.getById(userId);
//        String name = newUser.getName();
//        String username = newUser.getUsername();
//        String birthday = newUser.getBirthday();
//        System.out.println(user_update);
//        System.out.println(username);
//        System.out.println(birthday);
//
//
//        if (this.userRepository.findByUsername(username) != null && this.userRepository.findByUsername(username).getUsername() != username) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }else {
//            user_update.setUsername(username);
//            user_update.setName(name);
//            user_update.setBirthday(birthday);
//            this.userRepository.save(user_update);
//
//            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//        }
//    }



    @PostMapping("/users")
    ResponseEntity<User> createUser(@RequestBody User newUser, HttpServletRequest request) {
                return service.createUser(newUser, request);
    }

//    ResponseEntity<User> createUser(@RequestBody User newUser, HttpServletRequest request) {
//
//        String requestType = request.getHeader("requestType");
//        //  if the post is for register
//
//        if (requestType.contains("register")) {
//
//            try {
//                User create_user = this.service.createUser(newUser);
//                return new ResponseEntity<>(create_user, HttpStatus.CREATED);
//            } catch (Exception e) {
//                return new ResponseEntity<>(null,null,HttpStatus.CONFLICT);
//            }
//        }else if(requestType.contains("login")){
//            //  if the POST is for login
//            try {
//                String username = newUser.getUsername();
//                User user_found = this.userRepository.findByUsername(username);
//                if (user_found.getPassword().equals(newUser.getPassword())) {
//                    return new ResponseEntity<>(user_found, HttpStatus.FOUND);
//                } else {
//                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//                }
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}



