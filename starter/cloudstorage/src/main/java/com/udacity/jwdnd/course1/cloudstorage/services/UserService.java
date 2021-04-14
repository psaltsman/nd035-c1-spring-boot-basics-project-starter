package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controllers.SignupController;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {

        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public User getUser(String username) {

        logger.debug("getUser: " + username);

        //If the return object is null then the username has not already been used
        return userMapper.getUser(username);
    }

    /**
     * Creates a new user record.
     * @param user A {@link com.udacity.jwdnd.course1.cloudstorage.model.User} object
     * @return boolean True if the new record was created, false otherwise
     */
    public boolean createNewUser(User user) {

        logger.debug("createNewUser: " + user.getUsername());

        //Hash the password we are about to save using a random salt value
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        User toInsert = new User(
                null,
                user.getUsername(),
                encodedSalt,
                hashedPassword,
                user.getFirstName(),
                user.getLastName());

        //Return the number of rows created which we expect to be 1.  If it return 0 then the insert was unsuccessful
        return userMapper.insert(toInsert) > 0;
    }

    public Integer getUserIdByUsername(String userName) {

        return null;
    }
}
