package ru.sberbank.homework.dragonblog.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.dragonblog.frontend.model.UiComment;
import ru.sberbank.homework.dragonblog.model.Comment;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CommentConverter implements Converter<Comment, UiComment> {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("HH:MM:SS dd.MM.YYYY", Locale.getDefault())
            .withZone(ZoneOffset.UTC);

    @Override
    public UiComment convert(Comment source) {
        return UiComment.builder()
                .id(source.getId())
                .date(formatter.format(source.getDate()))
                .description(source.getDescription())
                .build();
    }

    public List<UiComment> convert(List<Comment> comments) {

        List<UiComment> uiComments = new ArrayList<>();

        for (Comment comment : comments) {
            uiComments.add(convert(comment));
        }

        return uiComments;
    }

}
