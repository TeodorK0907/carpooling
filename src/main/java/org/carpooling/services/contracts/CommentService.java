package org.carpooling.services.contracts;

import org.carpooling.models.Comment;

public interface CommentService {

    Comment getById(int commentId);

    Comment getByTravelId(int travelId);

    void create(String content, int travelId);

    void update(String content, int travelId);

}
