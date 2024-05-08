package org.carpooling.services.contracts;

import org.carpooling.helpers.UserFilterOptions;
import org.carpooling.models.User;

import java.util.List;

public interface UserService {
    List<User> getAll(UserFilterOptions filter);

    User getById(int userId);

    User getByUsername(String username);

    User getByEmail(String email);

    User getByPhoneNumber(String phone_number);

    User create(User user);

    User update(User user);

    void block(int id);

    void unblock(int id);

    void delete(int userId);

}
