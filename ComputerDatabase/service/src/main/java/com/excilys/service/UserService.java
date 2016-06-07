package com.excilys.service;

import com.excilys.model.User;

//TODO : COMMENT ME !
public interface UserService {

    User getByLogin(String login);

    User add(User user);

    void delete(Long id);
}