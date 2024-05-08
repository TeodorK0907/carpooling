package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.UserFilterOptions;
import org.carpooling.helpers.model_validators.UserFilterValidator;
import org.carpooling.helpers.model_validators.UserValidator;
import org.carpooling.models.User;
import org.carpooling.repositories.UserRepository;
import org.carpooling.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.carpooling.helpers.model_constants.ModelNames.USER;
import static org.carpooling.helpers.model_constants.attribute_constants.UserAttribute.*;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll(UserFilterOptions filter) {
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
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(User user) {
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
        userRepository.save(user);
        return user;
    }

    @Override
    public void block(int id) {
        User toBeBlocked = getById(id);
        toBeBlocked.setBlocked(true);
        userRepository.save(toBeBlocked);
    }

    @Override
    public void unblock(int id) {
        User toBeUnblocked = getById(id);
        toBeUnblocked.setBlocked(false);
        userRepository.save(toBeUnblocked);
    }

    @Override
    public void delete(int userId) {
        User toBeDeleted = getById(userId);
        toBeDeleted.setArchived(true);
        userRepository.save(toBeDeleted);
    }
}
