package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setCreationDate();

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    // update user info
    public ResponseEntity<User> updateUser(Long userId, User newUser) {

        User user_update = this.userRepository.getById(userId);
        String name = newUser.getName();
        String username = newUser.getUsername();
        String birthday = newUser.getBirthday();
        System.out.println(user_update);
        System.out.println(username);
        System.out.println(birthday);


        if (this.userRepository.findByUsername(username) != null && this.userRepository.findByUsername(username).getId() != userId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else {
            user_update.setUsername(username);
            user_update.setName(name);
            user_update.setBirthday(birthday);
            this.userRepository.save(user_update);

            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    //  register and login
    public ResponseEntity<User> createUser( User newUser, HttpServletRequest request) {

        String requestType = request.getHeader("requestType");
        //  if the post is for register

        if (requestType.contains("register")) {

            try {
                User create_user = this.createUser(newUser);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
