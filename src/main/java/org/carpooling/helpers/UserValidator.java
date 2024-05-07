package org.carpooling.helpers;

import org.carpooling.exceptions.DuplicateEntityException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.models.User;

import java.util.Optional;

public class UserValidator {
    public static boolean doesUsernameExist(User existing, User toBeCreated) {
        if ((existing.getUsername().equals(toBeCreated.getUsername()))
                && isIdDifferent(existing, toBeCreated)) {
            throw new DuplicateEntityException("user", "username", existing.getUsername());
        }
        return false;
    }

    public static boolean doesEmailExist(User existing, User toBeCreated) {
        if ((existing.getEmail().equals(toBeCreated.getEmail()))
                && isIdDifferent(existing, toBeCreated)) {
            throw new DuplicateEntityException("user", "email", existing.getEmail());
        }
        return false;
    }

    public static boolean doesPhoneNumberExist(User existing, User toBeCreated) {
        if ((existing.getPhoneNumber().equals(toBeCreated.getPhoneNumber()))
        && isIdDifferent(existing, toBeCreated)) {
            throw new DuplicateEntityException("user", "phoneNumber", existing.getPhoneNumber());
        }
        return false;
    }

    public static void validateIfUserIsEmpty(boolean isEmpty,
                                             String attribute,
                                             String value) {
        if (isEmpty) {
            throw new EntityNotFoundException("user", attribute, value);
        }
    }

    public static boolean validateIfUserIsEmpty(Optional<User> userToValidate) {
        return userToValidate.isEmpty();
    }

    public static boolean isIdDifferent(User existing, User toBeCreated) {
        return existing.getId() != toBeCreated.getId();
    }
}
