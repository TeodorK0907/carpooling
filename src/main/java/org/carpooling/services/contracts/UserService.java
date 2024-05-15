package org.carpooling.services.contracts;

import org.carpooling.helpers.model_filters.UserFilterOptions;
import org.carpooling.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<User> getAll(User authenticatedUser, UserFilterOptions filter);

    User getById(int userId);

    User getByUsername(String username);

    User getByEmail(String email);

    User getByPhoneNumber(String phone_number);

    User create(User user);

    User update(User authenticatedUser, User user);

    void block(User authenticatedUser, int userId);

    void unblock(User authenticatedUser, int userId);

    void delete(User authenticatedUser, int userId);

}
