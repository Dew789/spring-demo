package com.example.demo.spring.entity;

import lombok.Data;

@Data
public class UserDao {

    private Long id;

    private String userName;

    private String passWord;

    private String userSex;

    private String nickName;

    public UserDao(String userName, String passWord, String userSex) {
        this.userName = userName;
        this.passWord = passWord;
        this.userSex = userSex;
    }

}
