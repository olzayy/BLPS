package com.example.blps.service;

import com.example.blps.module.entity.User;

import java.util.Optional;

public interface UserService {

	User saveUser(User user);

	Optional<User> findUserByEmail(String email);
}
