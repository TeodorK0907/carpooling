package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.UserValidator;
import org.carpooling.models.User;
import org.carpooling.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("user", "id", String.valueOf(userId)));
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
    public void delete(int userId) {

    }
}
