package org.carpooling.services.contracts;

import org.carpooling.models.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(int userId);

    User create(User user);

    User update(User user);

    void delete(int userId);

}