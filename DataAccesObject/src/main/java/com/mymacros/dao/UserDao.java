package com.mymacros.dao;

import com.mymacros.dto.entity.UserDto;

/**
 * Created by Tomas on 17/07/2016.
 */
public interface UserDao
{
     void createUser(UserDto userDto);
     void getUser(long id);
}