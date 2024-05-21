package org.carpooling.helpers.validators;

public class CommentValidator {
    public static boolean isContentEmpty(String content) {
        if (content == null) {
            return true;
        }
        return content.isEmpty();
    }
}
