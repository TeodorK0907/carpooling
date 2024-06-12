package org.carpooling.services;

import org.carpooling.clients.contracts.MailJetClient;
import org.carpooling.helpers.constants.VerificationStatus;
import org.carpooling.helpers.handlers.MailJetResponseHandler;
import org.carpooling.helpers.validators.MailJetClientValidator;
import org.carpooling.models.helper_model.VerificationEmail;
import org.carpooling.repositories.EmailVerificationRepository;
import org.carpooling.services.contracts.EmailVerificationService;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final EmailVerificationRepository mailRepo;
    private final MailJetClient client;

    public EmailVerificationServiceImpl(EmailVerificationRepository mailRepo,
                                        MailJetClient client) {
        this.mailRepo = mailRepo;
        this.client = client;
    }

    @Override
    public void sendEmail(String userEmail) {
        String mailId = MailJetResponseHandler
                .extractMailId(client.sendEmail(userEmail));
        VerificationEmail mail = new VerificationEmail(
                mailId, userEmail, VerificationStatus.SENT
        );
        mailRepo.save(mail);
    }

    @Override
    public boolean isEmailVerified(String userEmail) {
        VerificationEmail mail = mailRepo.findByEmail(userEmail);
        return MailJetClientValidator
                .isLinkClicked(
                        MailJetResponseHandler
                                .extractStatus(client.viewEmailStatus(mail.getMailId()))
                );
    }
}
