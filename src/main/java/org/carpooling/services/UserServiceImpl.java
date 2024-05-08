package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.UserValidator;
import org.carpooling.models.User;
import org.carpooling.repositories.UserRepository;
import org.carpooling.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.carpooling.helpers.model_constants.ModelNames.USER;
import static org.carpooling.helpers.model_constants.attribute_constants.UserAttribute.ID;

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
    public List<User> getAll() {
        List<User> users = userRepository.findAllByArchivedIsFalse();
        UserValidator.validateIfUserListIsEmpty(users);
        return users;
    }

    @Override
    public User getById(int userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(USER.toString(), ID.toString(), String.valueOf(userId)));
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
        User toBeDeleted = getById(userId);
        toBeDeleted.setArchived(true);
        userRepository.save(toBeDeleted);
    }
}
