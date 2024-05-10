package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.UserFilterOptions;
import org.carpooling.helpers.validators.UserFilterValidator;
import org.carpooling.helpers.validators.UserValidator;
import org.carpooling.models.User;
import org.carpooling.repositories.UserRepository;
import org.carpooling.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> getAll(User authUser, UserFilterOptions filter) {
        UserValidator.isAdmin(authUser);
        List<User> users;
        if (UserFilterValidator.isFilterEmpty(filter)) {
            users = userRepository.findAllByArchivedIsFalse();
        } else {
            users = userRepository
                    .findAllByArchivedIsFalseAndUsernameLikeOrEmailLikeOrPhoneNumberLike(
                            UserFilterValidator.isFilterEmpty(filter.getUsername()),
                            UserFilterValidator.isFilterEmpty(filter.getEmail()),
                            UserFilterValidator.isFilterEmpty(filter.getPhoneNumber())
                    );
        }
        UserValidator.validateIfUserListIsEmpty(users);
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

    @Override
    public User update(User authUser, User user) {
        doesUserDataAlreadyExist(user);
        if (UserValidator.isIdDifferent(authUser, user)) {
            throw new UnauthorizedOperationException(
                    "You are unauthorized to perform the required action");
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
        Optional<User> userToValidate;
        userToValidate = userRepository.findUserByUsername(user.getUsername());
        if (!UserValidator.validateIfUserIsEmpty(userToValidate)) {
            UserValidator.doesUsernameExist(user, userToValidate.get());
        }
        userToValidate = userRepository.findUserByEmail(user.getEmail());
        if (!UserValidator.validateIfUserIsEmpty(userToValidate)) {
            UserValidator.doesEmailExist(user, userToValidate.get());
        }
        userToValidate = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if (!UserValidator.validateIfUserIsEmpty(userToValidate)) {
            UserValidator.doesPhoneNumberExist(user, userToValidate.get());
        }
    }
}
