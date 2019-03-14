package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.rules.ExpectedException;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername1"));

        User testUser = new User();
        testUser.setName("testName1");
        testUser.setUsername("testUsername1");
        testUser.setPassword("test");

        User createdUser = userService.createUser(testUser);

        Assert.assertNotNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.ONLINE);
        Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));
    }

    @Test
    public void duplicate_username() {
        Assert.assertNull(userRepository.findByUsername("testUsername2"));

        User testUser = new User();
        testUser.setName("testName2");
        testUser.setUsername("testUsername2");
        testUser.setPassword("test");
        User createdUser = userService.createUser(testUser);

        User testUser_2 = new User();
        testUser_2.setName("testName2");
        testUser_2.setUsername("testUsername2");
        testUser.setPassword("test");
        Assert.assertNull(userService.createUser(testUser_2));

        }

    @Test
    public void updateUser() {
        //  create a new user
        Assert.assertNull(userRepository.findByUsername("testUsername4"));

        User testUser = new User();
        testUser.setName("testName4");
        testUser.setUsername("testUsername4");
        testUser.setPassword("test4");
        User createdUser = userService.createUser(testUser);

        //  change the new username and save it
        Assert.assertNull(userRepository.findByUsername("123"));
        String new_username = "123";
        createdUser.setUsername(new_username);

        userRepository.save(createdUser);

        //  find by the new username and compare the name
        Assert.assertEquals(userRepository.findByUsername("123").getUsername(),"123");

    }
}
