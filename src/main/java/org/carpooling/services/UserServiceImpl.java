package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.model_filters.UserFilterOptions;
import org.carpooling.helpers.validators.UserFilterValidator;
import org.carpooling.helpers.validators.UserValidator;
import org.carpooling.models.User;
import org.carpooling.repositories.UserRepository;
import org.carpooling.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import static org.carpooling.helpers.constants.ModelNames.USER;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.*;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAll(User authUser, UserFilterOptions filter) {
        UserValidator.isAdmin(authUser);
        Page<User> users;
        Pageable page = PageRequest.of(0, 10);

        users = userRepository
                .findAllWithFilter(
                        String.format("%%%s%%", filter.getUsername().orElse("")),
                        String.format("%%%s%%", filter.getEmail().orElse("")),
                        String.format("%%%s%%", filter.getPhoneNumber().orElse("")),
                        page
                );

        UserValidator.isUserListEmpty(users);
        return users;
    }

    @Override
    public User getById(int userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(USER.toString(), ID.toString(), String.valueOf(userId)));

    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(USER.toString(), USERNAME.toString(), username));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(USER.toString(), EMAIL.toString(), email));
    }

    @Override
    public User getByPhoneNumber(String phone_number) {
        return userRepository.findUserByPhoneNumber(phone_number).orElseThrow(
                () -> new EntityNotFoundException(USER.toString(), USERNAME.toString(), phone_number));
    }

    @Override
    public User create(User user) {
        doesUserDataAlreadyExist(user);
        userRepository.save(user);
        return user;
    }

    //todo remove below magic String
    @Override
    public User update(User authUser, User user) {
        doesUserDataAlreadyExist(user);
        if (UserValidator.isIdDifferent(authUser, user)) {
            throw new UnauthorizedOperationException(
                    "You are unauthorized to perform the required action.");
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void block(User authUser, int id) {
        UserValidator.isAdmin(authUser);
        User toBeBlocked = getById(id);
        toBeBlocked.setBlocked(true);
        userRepository.save(toBeBlocked);
    }

    @Override
    public void unblock(User authUser, int id) {
        UserValidator.isAdmin(authUser);
        User toBeUnblocked = getById(id);
        toBeUnblocked.setBlocked(false);
        userRepository.save(toBeUnblocked);
    }

    @Override
    public void delete(User authUser, int userId) {

        User toBeDeleted = getById(userId);
        if (UserValidator.isIdDifferent(authUser, toBeDeleted)) {
            throw new UnauthorizedOperationException(
                    "You are unauthorized to perform the required action");
        }
        toBeDeleted.setArchived(true);
        userRepository.save(toBeDeleted);
    }

    private void doesUserDataAlreadyExist(User user) {
        User userToValidate;
        boolean isUsernameDuplicated = true;
        boolean isEmailDuplicated = true;
        try {
            userToValidate = getByUsername(user.getUsername());
            UserValidator.doesUsernameExist(user, userToValidate);
        } catch (EntityNotFoundException e) {
            isUsernameDuplicated = false;
        }
        if (!isUsernameDuplicated) {
            try {
                userToValidate = getByEmail(user.getEmail());
                UserValidator.doesEmailExist(user, userToValidate);
            } catch (EntityNotFoundException e) {
                isEmailDuplicated = false;
            }
        }
        if (!isEmailDuplicated) {
            try {
                userToValidate = getByPhoneNumber(user.getPhoneNumber());
                UserValidator.doesPhoneNumberExist(user, userToValidate);
            } catch (EntityNotFoundException e) {
            }
        }
    }
}