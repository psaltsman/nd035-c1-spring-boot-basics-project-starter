package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.pojos.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);
}
