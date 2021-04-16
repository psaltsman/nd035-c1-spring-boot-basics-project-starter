package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from credentials where userid = #{userId}")
    public List<Credential> getAllCredentialsByUser(User user);

    @Insert("insert into credentials (url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys=true, keyProperty="credentialId")
    public int insertCredential(Credential credential);

    @Update("update credentials set url=#{url}, username=#{username}, key=#{key}, password=#{password} where credentialid=#{credentialId}")
    public int updateCredential(Credential credential);

    @Delete("delete from credentials where credentialid=#{credentialId}")
    public int deleteCredential(Integer credential);

    @Select("select * from credentials where credentialid = #{credentialId}")
    public Credential getCredentialById(Integer credentialId);

    @Select("select key from credentials where credentialid = #{credentialId}")
    public String getKeyById(Integer credentialId);
}
