package ru.sberbank.homework.dragonblog.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.model.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class PostConverter implements Converter<Post, UiPost> {

    private final UserConverter userConverter;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("HH:mm:ss dd.MM.yyyy", Locale.getDefault());

    public PostConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public UiPost convert(Post source) {
        return UiPost.builder()
                .id(source.getId())
                .author(userConverter.convert(source.getAuthor()))
                .postDateTime(formatter.format(source.getPostDateTime()))
                .description(source.getDescription())
                .build();
    }

    public List<UiPost> convert(List<Post> posts) {

        List<UiPost> uiPosts = new ArrayList<>();

        if(posts.isEmpty()) {
            return uiPosts;
        }

        for (Post post : posts) {
            uiPosts.add(convert(post));
        }

        return uiPosts;
    }

    public Post convertBack(UiPost uiPost) {
        Post post = new Post();
        post.setId(uiPost.getId());
        post.setAuthor(userConverter.convertBack(uiPost.getAuthor()));
        post.setDescription(uiPost.getDescription());
        post.setPostDateTime(LocalDateTime.parse(uiPost.getPostDateTime(), formatter));
        return post;
    }

}
