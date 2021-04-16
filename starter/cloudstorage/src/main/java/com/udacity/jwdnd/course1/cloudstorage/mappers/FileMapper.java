package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("select * from files where userid = #{userId}")
    public List<File> getAllFiles(User user);

    @Insert("insert into files (filename, contenttype, filesize, userid, filedata) values (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys=true, keyProperty="fileId")
    public int insertFile(File file);

    @Select("select * from files where fileid = #{fileId}")
    public File getFileById(Integer fileId);

    @Delete("delete from files where fileid = #{fileId}")
    public int deleteFile(Integer fileId);
}
