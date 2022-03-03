package com.nomadlab.httpconnection.service;

import android.util.Log;

import com.google.gson.Gson;
import com.nomadlab.httpconnection.models.UserDto;
import com.nomadlab.httpconnection.repository.UserRepository;
import com.nomadlab.httpconnection.utils.HttpClient2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class UserService implements UserRepository {

    private UserDto userDto;

    @Override
    public void create(String id, String pw) {
    }

    /**
     * 회원 정보 조회
     * @param id 사용자 계정
     * @return UserDto (회원정보)
     */
    @Override
    public UserDto read(String id) {
        new Thread(() -> {
            HttpURLConnection conn = HttpClient2.getInstance().getConnection("users/"+id, HttpClient2.GET);
            try {
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(),
                                    StandardCharsets.UTF_8));
                    userDto = new Gson().fromJson(reader, UserDto.class);
                    Log.d("TAG", userDto.getUsername() + " : <-----");
                } else {
                    Log.d("TAG", "에러 상태코드 :  " + conn.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }).start();
        return userDto;
    }

    @Override
    public void update(UserDto userDto) {

    }

    @Override
    public void delete(String id) {

    }
}
