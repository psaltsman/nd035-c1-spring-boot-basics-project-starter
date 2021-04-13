package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (userid, url, username, key, password) VALUES(#{userId}, #{url}, #{username}, #{key}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);
}
