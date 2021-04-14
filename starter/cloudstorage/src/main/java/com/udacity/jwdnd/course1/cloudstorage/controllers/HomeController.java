package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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

    private NoteService noteService;
    private UserService userService;

    public HomeController(NoteService noteService, UserService userService) {

        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {

        logger.debug("index");

        User authenticatedUser = userService.getUser(authentication.getName());

        List<Note> allUserNotes = noteService.getAllNotes(authenticatedUser);
        model.addAttribute("allUserNotes", allUserNotes);

        logger.debug("allUserNotes: = " + allUserNotes.size());

        for (Note note : allUserNotes) {

            logger.debug(">>> " + note.getNoteId() + ": " + note.getNoteTitle());
        }

        return "home";
    }

    @PostMapping(path="/note")
    public String handleNote(@ModelAttribute Note note, Model model, Authentication authentication) {

        logger.debug("handleNote");

        User authenticatedUser = userService.getUser(authentication.getName());

        String successMsg = null;
        String errorMsg = null;

        String noteDescription = note.getNoteDescription();

        //For some reason copy/pasting into the description is allowing values greater than the db column width
        if (noteDescription != null && noteDescription.length() > 1000) {

            noteDescription = noteDescription.substring(0, 999);
            note.setNoteDescription(noteDescription);
        }

        if (note.getNoteId() == null) {

            logger.debug("saving new note");
            note.setUserId(authenticatedUser.getUserid());

            logger.debug("userid: " + note.getUserId());
            logger.debug("title: " + note.getNoteTitle());
            logger.debug("description: " + note.getNoteDescription());

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
}
