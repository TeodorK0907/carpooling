package org.carpooling.helpers.validators;

import org.carpooling.exceptions.DuplicateEntityException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.constants.UserRole;
import org.carpooling.models.User;

import java.util.List;
import java.util.Optional;

import static org.carpooling.helpers.constants.ModelNames.USER;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.*;

public class UserValidator {
    public static boolean doesUsernameExist(User existing, User toBeCreated) {
        if ((existing.getUsername().equals(toBeCreated.getUsername()))
                && isIdDifferent(existing, toBeCreated)) {
            throw new DuplicateEntityException(USER.toString(), USERNAME.toString(), existing.getUsername());
        }
        return false;
    }

    public static boolean doesEmailExist(User existing, User toBeCreated) {
        if ((existing.getEmail().equals(toBeCreated.getEmail()))
                && isIdDifferent(existing, toBeCreated)) {
            throw new DuplicateEntityException(USER.toString(), EMAIL.toString(), existing.getEmail());
        }
        return false;
    }

    public static boolean doesPhoneNumberExist(User existing, User toBeCreated) {
        if ((existing.getPhoneNumber().equals(toBeCreated.getPhoneNumber()))
        && isIdDifferent(existing, toBeCreated)) {
            throw new DuplicateEntityException(USER.toString(), PHONE_NUMBER.toString(), existing.getPhoneNumber());
        }
        return false;
    }

    public static void validateIfUserIsEmpty(boolean isEmpty,
                                             String attribute,
                                             String value) {
        if (isEmpty) {
            throw new EntityNotFoundException(USER.toString(), attribute, value);
        }
    }

    public static void validateIfUserListIsEmpty(List<User> list) {
        if (list.isEmpty()) {
            throw new EntityNotFoundException("There are no active users");
        }
    }

    public static boolean validateIfUserIsEmpty(Optional<User> userToValidate) {
        return userToValidate.isEmpty();
    }

    public static boolean isIdDifferent(User existing, User fromDB) {
        return existing.getId() != fromDB.getId();
    }

    public static boolean isAdmin(User user) {
        if (!user.getRole().equals(UserRole.ADMIN)){
            throw new UnauthorizedOperationException("You are unauthorized to perform the required action");
        }
        return true;
    }
}
