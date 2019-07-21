package ru.sberbank.homework.dragonblog.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.dragonblog.frontend.model.UiComment;
import ru.sberbank.homework.dragonblog.model.Comment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CommentConverter implements Converter<Comment, UiComment> {
    private final UserConverter userConverter;
    private final PostConverter postConverter;

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("HH:mm:ss dd.MM.yyyy", Locale.getDefault())
            .withZone(ZoneOffset.UTC);

    public CommentConverter(UserConverter userConverter, PostConverter postConverter) {
        this.userConverter = userConverter;
        this.postConverter = postConverter;
    }

    @Override
    public UiComment convert(Comment source) {
        return UiComment.builder()
                .id(source.getId())
                .author(userConverter.convert(source.getAuthor()))
                .post(postConverter.convert(source.getPost()))
                .date(formatter.format(source.getDate()))
                .description(source.getDescription())
                .build();
    }

    public List<UiComment> convert(List<Comment> comments) {
        List<UiComment> uiComments = new ArrayList<>();

        if(comments.isEmpty()) {
            return uiComments;
        }

        for (Comment comment : comments) {
            uiComments.add(convert(comment));
        }

        return uiComments;
    }

    public Comment convertBack(UiComment uiComment) {
        Comment comment = new Comment();
        comment.setId(uiComment.getId());
        comment.setAuthor(userConverter.convertBack(uiComment.getAuthor()));
        comment.setPost(postConverter.convertBack(uiComment.getPost()));
        comment.setDate(LocalDateTime.parse(uiComment.getDate(), formatter));
        comment.setDescription(uiComment.getDescription());
        return comment;
    }

}
