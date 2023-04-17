package com.example.backend.service;

import com.example.backend.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
