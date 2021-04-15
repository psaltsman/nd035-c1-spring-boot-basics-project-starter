package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService) {

        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {

        logger.debug("index");

        User authenticatedUser = userService.getUser(authentication.getName());

        List<Note> allUserNotes = noteService.getAllNotes(authenticatedUser);
        model.addAttribute("allUserNotes", allUserNotes);

        logger.debug("allUserNotes: = " + allUserNotes.size());

        List<Credential> allUserCredentials = credentialService.getAllCredentials(authenticatedUser);
        model.addAttribute("allUserCredentials", allUserCredentials);

        logger.debug("allUserCredentials: = " + allUserCredentials.size());

        return "home";
    }

    @PostMapping(path="/note")
    public String handleNote(@ModelAttribute Note note, Model model, Authentication authentication) {

        logger.debug("handleNote");
        logger.debug("title: " + note.getNoteTitle());
        logger.debug("description: " + note.getNoteDescription());
        logger.debug("userId: " + note.getUserId());

        User authenticatedUser = userService.getUser(authentication.getName());

        String successMsg = null;
        String errorMsg = null;

        //For some reason copy/pasting into the description textarea is allowing values greater than the db column width
        //and greater than the input field's maxLength.
        String noteDescription = note.getNoteDescription();

        if (noteDescription != null && noteDescription.length() > 1000) {

            noteDescription = noteDescription.substring(0, 999);
            note.setNoteDescription(noteDescription);
        }

        if (note.getNoteId() == null) {

            logger.debug("saving new note");

            note.setUserId(authenticatedUser.getUserid());

            try {

                noteService.insertNote(note);

            } catch (Exception ex) {

                errorMsg = "Your note could not be saved due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
            }

            successMsg = "Your note was successfully saved.";

        } else {

            logger.debug("updating existing note");

            try {

                noteService.updateNote(note);

            } catch (Exception ex) {

                errorMsg = "Your note could not be updated due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
            }

            successMsg = "Your note was successfully updated.";
        }

        if (errorMsg != null) {

            model.addAttribute("errorMsg", errorMsg);

        } else {

            model.addAttribute("successMsg", successMsg);
        }

        return "result";
    }

    @PostMapping(path="/credential")
    public String handleCredential(@ModelAttribute Credential credential, Model model, Authentication authentication) {

        logger.debug("handleCredential");

        logger.debug("credentialId: " + credential.getCredentialId());
        logger.debug("url: " + credential.getUrl());
        logger.debug("username: " + credential.getUsername());
        logger.debug("key: " + credential.getKey());
        logger.debug("password: " + credential.getPassword());
        logger.debug("userId: " + credential.getUserId());

        User authenticatedUser = userService.getUser(authentication.getName());

        String successMsg = null;
        String errorMsg = null;

        if (credential.getCredentialId() == null) {

            logger.debug("saving new credential");
            credential.setUserId(authenticatedUser.getUserid());

            try {

                credentialService.insertCredential(credential);

            } catch (Exception ex) {

                errorMsg = "Your credential could not be saved due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
            }

            successMsg = "Your credential was successfully saved.";

        } else {

            logger.debug("updating existing credential");

            try {

                credentialService.updateCredential(credential);

            } catch (Exception ex) {

                errorMsg = "Your credential could not be updated due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
            }

            successMsg = "Your credential was successfully updated.";
        }

        if (errorMsg != null) {

            model.addAttribute("errorMsg", errorMsg);

        } else {

            model.addAttribute("successMsg", successMsg);
        }

        return "result";
    }

    @PostMapping(path="/note/delete")
    public String deleteNote(@ModelAttribute Note note, Model model) {

        logger.debug("deleteNote");

        String successMsg = null;
        String errorMsg = null;

        try {

            noteService.deleteNote(note);

        } catch (Exception ex) {

            errorMsg = "Your note could not be deleted due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
        }

        successMsg = "Your note was successfully deleted.";

        if (errorMsg != null) {

            model.addAttribute("errorMsg", errorMsg);

        } else {

            model.addAttribute("successMsg", successMsg);
        }

        return "result";
    }

    @PostMapping(path="/credential/delete")
    public String deleteCredential(@ModelAttribute Credential credential, Model model) {

        logger.debug("deleteCredential");

        String successMsg = null;
        String errorMsg = null;

        try {

            credentialService.deleteCredential(credential);

        } catch (Exception ex) {

            errorMsg = "Your credential could not be deleted due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
        }

        successMsg = "Your credential was successfully deleted.";

        if (errorMsg != null) {

            model.addAttribute("errorMsg", errorMsg);

        } else {

            model.addAttribute("successMsg", successMsg);
        }

        return "result";
    }

    @GetMapping(value="/getCredentialById")
    @ResponseBody
    public Credential getCredentialById(@RequestParam(value="credentialId", required=true) String credentialId) {

        return credentialService.getCredentialById(Integer.parseInt(credentialId));
    }
}
