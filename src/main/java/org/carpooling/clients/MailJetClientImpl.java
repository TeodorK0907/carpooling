package org.carpooling.clients;

import org.carpooling.clients.contracts.MailJetClient;
import org.carpooling.config.mail_jet.MailJetWebClientConfig;
import org.carpooling.exceptions.BadRequestException;
import org.carpooling.helpers.constants.mail_jet_client.MailJetClientEndpoint;
import org.carpooling.helpers.handlers.MailJetClientHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MailJetClientImpl implements MailJetClient {
    //todo set sender email as a property, recipient email to be taken as a param
    private static final String SENDER_EMAIL = "";
    private static final String RECIPIENT_EMAIL = "";
    private final MailJetWebClientConfig client;

    @Autowired
    public MailJetClientImpl(MailJetWebClientConfig client) {
        this.client = client;
    }

    @Override
    public String sendEmail(String recipientEmail) {
        String body = MailJetClientHandler.handleRequestBody(SENDER_EMAIL, RECIPIENT_EMAIL);
        String response = client.getWithMailJetBaseUrl()
                .post()
                .uri(MailJetClientEndpoint.SEND.toString())
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                            return clientResponse.bodyToMono(String.class).flatMap(errorResponse -> {
                                return Mono.error(new BadRequestException(errorResponse));
                            });
                        }
                )
                .bodyToMono(String.class)
                .block();
        System.out.println(response);
        return response;
    }

    @Override
    public String viewEmailStatus() {
        return null;
    }
}
