package org.carpooling.services;

import org.carpooling.exceptions.EntityNotFoundException;
import org.carpooling.helpers.constants.ModelNames;
import org.carpooling.helpers.constants.attribute_constants.CommentAttribute;
import org.carpooling.helpers.validators.CommentValidator;
import org.carpooling.models.Comment;
import org.carpooling.repositories.CommentRepository;
import org.carpooling.services.contracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getById(int commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ModelNames.COMMENT.toString(),
                        CommentAttribute.ID.toString(),
                        String.valueOf(commentId))
                );
    }

    @Override
    public Comment getByTravelId(int travelId) {
          return commentRepository.findByTravelId(travelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ModelNames.COMMENT.toString(),
                        CommentAttribute.TRAVEL_ID.toString(),
                        String.valueOf(travelId))
                );
    }

    @Override
    public void create(String content, int travelId) {
        if (!CommentValidator.isContentEmpty(content)) {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setTravelId(travelId);
            commentRepository.save(comment);
        }
    }

    @Override
    public void update(String content, int travelId) {
        if (!CommentValidator.isContentEmpty(content)) {
            try {
                Comment comment = getByTravelId(travelId);
                comment.setContent(content);
                commentRepository.save(comment);
            } catch (EntityNotFoundException e) {
                Comment comment = new Comment();
                comment.setContent(content);
                commentRepository.save(comment);
            }
        }
    }
}