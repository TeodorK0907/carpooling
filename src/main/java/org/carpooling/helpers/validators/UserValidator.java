package org.carpooling.helpers.validators;

import org.carpooling.exceptions.DuplicateEntityException;
import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.constants.UserRole;
import org.carpooling.helpers.errors.UserValidatorErrors;
import org.carpooling.models.User;

import java.util.List;
import java.util.Optional;

import static org.carpooling.helpers.constants.ModelNames.USER;
import static org.carpooling.helpers.constants.attribute_constants.UserAttribute.*;

public class UserValidator {

    //todo consider optimization for boolean method to take more params, but be a single method for every attribute
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

    public static void isUserDeleted(User user) {
        if (user.isArchived()) {
            throw new EntityNotFoundException(USER.toString(), ID.toString(), String.valueOf(user.getId()));
        }
    }

    public static boolean isUserListEmpty(List<User> list) {
        if (list.isEmpty()) {
            throw new EntityNotFoundException(UserValidatorErrors.NO_USERS_FOUND.toString());
        }
        return false;
    }

    public static boolean validateIfUserIsEmpty(Optional<User> userToValidate) {
        return userToValidate.isEmpty();
    }

    public static boolean isIdDifferent(User existing, User fromDB) {
        return existing.getId() != fromDB.getId();
    }

    public static boolean isAdmin(User user) {
        if (!user.getRole().equals(UserRole.ADMIN)){
            throw new UnauthorizedOperationException(UserValidatorErrors.UNAUTHORIZED.toString());
        }
        return true;
    }

    public static boolean isBlocked(User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(UserValidatorErrors.UNAUTHORIZED.toString());
        }
        return false;
    }

    public static boolean isVerified(User user) {
        if (user.isVerified()) {
            throw new UnauthorizedOperationException(UserValidatorErrors.UNAUTHORIZED.toString());
        }
        return false;
    }
}