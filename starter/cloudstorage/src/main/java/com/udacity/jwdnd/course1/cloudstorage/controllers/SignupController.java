package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private Logger logger = LoggerFactory.getLogger(SignupController.class);

    private UserService userService;

    public SignupController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public String index() {

        logger.debug("signup");

        return "signup";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {

        logger.debug("createUser");

        String signupError = null;

        try {

            userService.createNewUser(user);

        } catch (DuplicateKeyException dke) {

            signupError = "That username is not available";

        } catch (Exception ex) {

            signupError = "An error occurred when attempting to create your account: " + ex.getClass().getName() + " - " + ex.getMessage();
        }

        if (signupError != null) {

            model.addAttribute("signupError", signupError);
            return "signup";
        } else {

            redirectAttributes.addFlashAttribute("successMsg","Your account has been created");
            return "redirect:/login";
        }
    }
}
