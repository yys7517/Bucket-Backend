package org.example.bucketsearch.service;

import org.example.bucketsearch.domain.User;

public interface UserService {
    public User findUserByEmailAndPassword(String email, String password);
}
