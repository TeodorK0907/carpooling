package org.carpooling.services;

import org.carpooling.services.contracts.EmailVerificationService;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    //todo create a emailVerifcationRepo and inject it
    public EmailVerificationServiceImpl() {

    }
    //todo finish method body
    @Override
    public void sendEmail(String userEmail) {

    }
    //todo finish method body
    @Override
    public boolean isEmailVerified(String userEmail) {
        return false;
    }
}
