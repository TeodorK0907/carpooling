package org.carpooling.clients;

import org.carpooling.clients.contracts.MailJetClient;
import org.carpooling.config.mail_jet.MailJetWebClientConfig;
import org.carpooling.exceptions.BadRequestException;
import org.carpooling.exceptions.UnsuccessfulResponseException;
import org.carpooling.helpers.constants.mail_jet_client.MailJetClientEndpoint;
import org.carpooling.helpers.errors.MailJetClientErrors;
import org.carpooling.helpers.handlers.MailJetRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MailJetClientImpl implements MailJetClient {

    @Value("${mail.jet.sender-email}")
    private String SENDER_EMAIL;
    private static final String RECIPIENT_EMAIL = "";
    private final MailJetWebClientConfig client;

    @Autowired
    public MailJetClientImpl(MailJetWebClientConfig client) {
        this.client = client;
    }

    @Override
    public String sendEmail(String recipientEmail) {
        String body = MailJetRequestHandler.handleRequestBody(SENDER_EMAIL, RECIPIENT_EMAIL);
        String response = client.getWithMailJetBaseUrl()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(MailJetClientEndpoint.V3PointOne.toString())
                        .path(MailJetClientEndpoint.SEND.toString())
                        .build())
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                            return clientResponse.bodyToMono(String.class)
                                    .flatMap(errorResponse -> {
                                return Mono.error(new UnsuccessfulResponseException
                                        (MailJetClientErrors.FAILED_RESPONSE.toString())
                                );
                            });
                        }
                )
                .bodyToMono(String.class)
                .block();
        // System.out.println(response);
        return response;
    }
    //todo add query param and test method
    @Override
    public String viewEmailStatus(String mailId) {
        String response = client.getWithMailJetBaseUrl()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(MailJetClientEndpoint.V3.toString())
                        .path(MailJetClientEndpoint.MESSAGE.toString())
                        .path("/" + mailId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorResponse -> {
                                return Mono.error(new UnsuccessfulResponseException
                                        (MailJetClientErrors.FAILED_RESPONSE.toString())
                                );
                            });
                }
                )
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
