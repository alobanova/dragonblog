package ru.sberbank.homework.dragonblog.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.model.Post;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class PostConverter implements Converter<Post, UiPost> {

    private final CommentConverter commentConverter;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("HH:MM:SS dd.MM.YYYY", Locale.getDefault());

    public PostConverter(CommentConverter commentConverter) {
        this.commentConverter = commentConverter;
    }

    @Override
    public UiPost convert(Post source) {
        return UiPost.builder()
                .id(source.getId())
                .postDateTime(formatter.format(source.getPostDateTime()))
                .description(source.getDescription())
                .comments(commentConverter.convert(source.getComments()))
                .build();
    }

    public List<UiPost> convert(List<Post> posts) {

        List<UiPost> uiPosts = new ArrayList<>();

        for (Post post : posts) {
            uiPosts.add(convert(post));
        }

        return uiPosts;
    }

}
