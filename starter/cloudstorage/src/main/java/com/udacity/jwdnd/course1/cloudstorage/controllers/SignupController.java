package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private Logger logger = LoggerFactory.getLogger(SignupController.class);

    private UserService userService;

    public SignupController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public String signup() {

        logger.debug("signup");

        return "signup";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, Model model) {

        logger.debug("createUser");

        //Check for an existing user before proceeding
        if (userService.isExistingUserName(user.getUsername())) {

            model.addAttribute("signupError", "The username already exists.  Please enter a different one.");
        //Continue with the account creation
        } else {

            //Success!
            if (userService.createNewUser(user)) {

                model.addAttribute("signupSuccess", "");

            } else {

                model.addAttribute("signupError", "An error occurred and your account could not be created.");
            }
        }

        return "signup";
    }
}
