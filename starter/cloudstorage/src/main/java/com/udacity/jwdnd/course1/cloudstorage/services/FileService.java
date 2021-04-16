package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final Logger logger = LoggerFactory.getLogger(CredentialService.class);

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {

        this.fileMapper = fileMapper;
    }

    public List<File> getAllFiles(User user) {

        return fileMapper.getAllFiles(user);
    }

    public int insertFile(File file) {

        return fileMapper.insertFile(file);
    }

    public File getFileById(Integer fileId) {

        return fileMapper.getFileById(fileId);
    }

    public int deleteFile(Integer fileId) {

        return fileMapper.deleteFile(fileId);
    }
}
