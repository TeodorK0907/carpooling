package org.carpooling.services.contracts;

public interface EmailVerificationService {
    void sendEmail(String userEmail);

    boolean isEmailVerified(String userEmail);
}
