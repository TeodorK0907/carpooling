package org.carpooling.helpers.validators;

import org.carpooling.helpers.constants.VerificationStatus;

public class MailJetClientValidator {
    public static boolean isLinkClicked(String linkStatus) {
        return VerificationStatus.CLICKED.toString().equals(linkStatus);
    }
}
