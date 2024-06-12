package org.carpooling.security;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.exceptions.UnauthenticatedRequestException;
import org.carpooling.exceptions.UnauthorizedOperationException;
import org.carpooling.helpers.errors.AuthenticationErrors;
import org.carpooling.helpers.errors.UserValidatorErrors;
import org.carpooling.models.User;
import org.carpooling.services.contracts.EmailVerificationService;
import org.carpooling.services.contracts.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import static org.apache.tomcat.websocket.Constants.AUTHORIZATION_HEADER_NAME;

@Component
public class AuthenticationManager {
    private final UserService userService;
    private final EmailVerificationService mailService;

    public AuthenticationManager(UserService userService,
                                 EmailVerificationService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    public User fetchUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new UnauthenticatedRequestException(AuthenticationErrors.AUTH_FAILED.toString());
        }
        String authHeader = headers.getFirst(AUTHORIZATION_HEADER_NAME);

        String username = getUsername(authHeader);
        String password = getPassword(authHeader);
        try {
            User user = userService.getByUsername(username);
            if (!doesPasswordMatch(password, user.getPassword())) {
                throw new UnauthenticatedRequestException(AuthenticationErrors.AUTH_DETAILS_MISMATCH.toString());
            }
            if (!user.isVerified()) {
                if (!mailService.isEmailVerified(user.getEmail())) {
                    throw new UnauthorizedOperationException(UserValidatorErrors.UNAUTHORIZED.toString());
                } else {
                    userService.update(user);
                }
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw new UnauthenticatedRequestException(AuthenticationErrors.AUTH_DETAILS_MISMATCH.toString());
        }
    }

    private boolean doesPasswordMatch(String inputPass, String userPass) {
        return inputPass.equals(userPass);
    }

    private String getPassword(String authHeader) {
        int firstIntervalIndex = authHeader.indexOf(" ");
        if (firstIntervalIndex == -1) {
            throw new UnauthenticatedRequestException(AuthenticationErrors.AUTH_FAILED.toString());
        }
        return authHeader.substring(firstIntervalIndex + 1);
    }

    private String getUsername(String authHeader) {
        int firstIntervalIndex = authHeader.indexOf(" ");
        if (firstIntervalIndex == -1) {
            throw new UnauthenticatedRequestException(AuthenticationErrors.AUTH_FAILED.toString());
        }
        return authHeader.substring(0, firstIntervalIndex);
    }
}
