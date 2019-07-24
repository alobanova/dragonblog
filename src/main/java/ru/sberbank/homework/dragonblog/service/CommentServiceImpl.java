package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.dragonblog.frontend.converter.CommentConverter;
import ru.sberbank.homework.dragonblog.frontend.model.UiComment;
import ru.sberbank.homework.dragonblog.model.Comment;
import ru.sberbank.homework.dragonblog.repository.CommentRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mart
 * 01.07.2019
 **/
@Service
public class CommentServiceImpl {

    private final CommentConverter converter;
    private CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, CommentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public void update(long id, String description) throws NotFoundException {
        repository.update(id, description);
    }

    public void delete(long id) throws NotFoundException {
        repository.deleteById(id);
    }

    public UiComment create(UiComment comment) {
        Comment saveComment = repository.save(converter.convertBack(comment));

        if (saveComment != null) {
            return converter.convert(saveComment);
        }

        return null;
    }

    public List<UiComment> getAllByPost(long postId) {
        Optional<List<Comment>> allByPostId = repository.findAllByPostIdOrderByDate(postId);

        List<UiComment> uiComments = new ArrayList<>();
        allByPostId.ifPresent(comments -> uiComments.addAll(converter.convert(comments)));

        return uiComments;
    }

    public UiComment getFirstByDate(long postId) {
        Comment comment = repository.findFirstByPostIdOrderByDateDesc(postId);

        if(comment != null) {
            return converter.convert(comment);
        }
        return null;
    }

    public UiComment getById(long id) {
        Comment comment = repository.findById(id);

        if (comment != null) {
            return converter.convert(comment);
        }

        return null;
    }
}
