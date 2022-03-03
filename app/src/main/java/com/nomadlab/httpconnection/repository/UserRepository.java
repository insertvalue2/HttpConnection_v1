package com.nomadlab.httpconnection.repository;

import com.nomadlab.httpconnection.models.UserDto;

public interface UserRepository {

    void create(String id, String pw);
    UserDto read(String id);
    void update(UserDto userDto);
    void delete(String id);

}
