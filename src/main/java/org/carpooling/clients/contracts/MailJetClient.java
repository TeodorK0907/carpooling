package org.carpooling.clients.contracts;

public interface MailJetClient {
     String sendEmail(String recipientEmail);

     String viewEmailStatus(Long mailId);
}
