package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {

        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String index(Authentication authentication, Model model) {

        logger.debug("index");

        User authenticatedUser = userService.getUser(authentication.getName());
        model.addAttribute("authenticatedUser", authenticatedUser);

        List<File> allUserFiles = fileService.getAllFiles(authenticatedUser);
        model.addAttribute("allUserFiles", allUserFiles);

        logger.debug("allUserFiles: = " + allUserFiles.size());

        List<Note> allUserNotes = noteService.getAllNotes(authenticatedUser);
        model.addAttribute("allUserNotes", allUserNotes);

        logger.debug("allUserNotes: = " + allUserNotes.size());

        List<Credential> allUserCredentials = credentialService.getAllCredentials(authenticatedUser);
        model.addAttribute("allUserCredentials", allUserCredentials);

        logger.debug("allUserCredentials: = " + allUserCredentials.size());

        return "home";
    }

    @PostMapping(path="/upload")
    public String uploadFile(@RequestParam(value="fileUpload", required=true) MultipartFile fileUpload, Model model, Authentication authentication) {

        logger.debug("upload");

        String successMsg = null;
        String errorMsg = null;

        try {

            String originalName = fileUpload.getOriginalFilename();

            if (originalName != null && !originalName.equals("")) {

                File file = new File();
                file.setFileName(originalName);
                file.setContentType(fileUpload.getContentType());
                file.setFileSize(String.valueOf(fileUpload.getSize()));
                file.setUserId(userService.getUser(authentication.getName()).getUserid());
                file.setFileData(fileUpload.getBytes());

                fileService.insertFile(file);

            } else {

                throw new Exception("You did not select a file");
            }

        } catch (DuplicateKeyException dke) {

            errorMsg = "It appears that you have already uploaded that file";

        } catch (Exception ex) {

            errorMsg = "Your file could not be saved due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
        }

        successMsg = "Your file was successfully saved.";

        if (errorMsg != null) {

            model.addAttribute("errorMsg", errorMsg);

        } else {

            model.addAttribute("successMsg", successMsg);
        }


        return "result";
    }

    @GetMapping(path="/view")
    public ResponseEntity viewFile(@RequestParam(value="fileId", required=true) String fileId) {

        logger.debug("viewFile");
        logger.debug("fileId=" + fileId);

        File file = fileService.getFileById(Integer.parseInt(fileId));

        logger.debug(file.getFileName());
        logger.debug(file.getContentType());
        logger.debug(file.getFileSize());
        logger.debug("" + file.getFileData().length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getFileData());
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

    @PostMapping(path="/delete/{type}/{id}")
    public String handleDelete(@PathVariable(name="type") String type, @PathVariable(name="id") String id, Model model) {

        logger.debug("delete/" + type + "/" + id);

        String successMsg = null;
        String errorMsg = null;

        try {

            Integer idToDelete = Integer.parseInt(id);

            switch(type) {

                case "file":
                    fileService.deleteFile(idToDelete);
                    break;

                case "note":
                    noteService.deleteNote(idToDelete);
                    break;

                case "credential":
                    credentialService.deleteCredential(idToDelete);
                    break;
            }

        } catch (Exception ex) {

            errorMsg = "The " + type + " could not be deleted due to the following error: " + ex.getClass().getName() + " - " + ex.getMessage();
        }

        successMsg = "The " + type + " was successfully deleted.";

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
