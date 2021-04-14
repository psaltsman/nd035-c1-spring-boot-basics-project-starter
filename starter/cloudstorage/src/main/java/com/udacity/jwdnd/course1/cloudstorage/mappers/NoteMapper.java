package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("select * from notes where userid = #{userId}")
    public List<Note> getAllNotesByUser(User user);

    @Insert("insert into notes (notetitle, notedescription, userid) values (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys=true, keyProperty="noteId")
    public int insertNote(Note note);

    @Update("update notes set notetitle=#{noteTitle}, notedescription=#{noteDescription} where noteid=#{noteId}")
    public int updateNote(Note note);

    @Delete("delete from notes where noteid=#{noteId}")
    public int deleteNote(Note note);
}
