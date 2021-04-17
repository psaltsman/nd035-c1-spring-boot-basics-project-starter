package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final Logger logger = LoggerFactory.getLogger(NoteService.class);

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {

        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(User user) {

        return noteMapper.getAllNotesByUser(user);
    }

    public int insertNote(Note note) {

        return noteMapper.insertNote(note);
    }

    public int updateNote(Note note) {

        return noteMapper.updateNote(note);
    }

    public int deleteNote(Integer noteId) {

        return noteMapper.deleteNote(noteId);
    }
}
