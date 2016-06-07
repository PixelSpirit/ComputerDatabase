package com.excilys.persistence;

import com.excilys.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {

    User findByLogin(String login);
}
