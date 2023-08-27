package com.example.demo.spring;


import com.example.demo.spring.entity.UserDao;

public interface UserService {

    UserDao getUser(long id);
}
