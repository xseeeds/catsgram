package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.catsgram.model.Comment;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.CommentService;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;

    /*@GetMapping
    public Collection<PostWithCommentsDto> getPosts() {
        // выгружаем посты (один запрос)
        Map<Long, Post> postMap = postService.findAllPostsWithAuthors()
                .stream()
                .collect(Collectors.toMap(Post::getId, Function.identity()));
        // выгружаем комментарии (ещё один запрос)
        Map<Long, List<Comment>> commentMap = commentService.getByPostId(postMap.keySet())
                .stream()
                .collect(Collectors.groupingBy(Comment::getPostId));
        // готовим окончательный результат из полученных данных (нет обращений к БД)
        return postMap.values()
                .stream()
                .map(post -> makePostWithCommentsDto(
                        post,
                        commentMap.getOrDefault(post.getId(), Collections.emptyList())
                ))
                .collect(Collectors.toList());
    }

    private PostWithCommentsDto makePostWithCommentsDto(Post post, List<Comment> comments) {
        // конвертируем комментарии в DTO
        List<CommentDto> commentDtos = comments
                .stream()
                .map(comment -> CommentDto.of(comment.getId(), comment.getText(), comment.getPostId()))
                .collect(Collectors.toList());
        // формируем окончательное представление для данного поста
        User author = post.getAuthor();

        return PostWithCommentsDto.of(
                post.getId(), post.getTitle(), post.getDescription(),
                UserDto.of(author.getId(), author.getUsername(), author.getEmail()),
                commentDtos
        );
    }*/
}
