package ru.yandex.practicum.catsgram.controller;


import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.modelSchema.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static ru.yandex.practicum.catsgram.constant.Constant.DESCENDING_ORDER;
import static ru.yandex.practicum.catsgram.constant.Constant.SORTS;


@RestController
@Slf4j
@Validated
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/posts")
    public Collection<Post> findAll(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sort", defaultValue = DESCENDING_ORDER, required = false) String sort) {

        if (!SORTS.contains(sort)) {
            throw new IncorrectParameterException("sort");
        }
        if (page < 1) {
            throw new IncorrectParameterException("page");
        }
        if (size <= 0) {
            throw new IncorrectParameterException("size");
        }


        int from = page * size - size;

        final Collection<Post> posts = postService.findAllPost(size, from, sort);

        log.debug("Запрошено количество постов: {}", posts.size());

        return posts;
    }

    @PostMapping("/post")
    public Post create(@RequestBody Post post) {
        log.debug("post {}", post);
        return postService.create(post);
    }


    @GetMapping("/posts/{postId}")
    public Post findById(@PathVariable(required = false) Long postId) {
        if (postId != null) {
            if (postId <= 0) {
                throw new IncorrectParameterException("postId=>" + postId);
            }
            return postService.findPostById(postId);
        }
        throw new ValidationException("id missing");
    }

    @GetMapping("/posts/search")
    public Collection<Post> searchPostsByAuthorOnLocalDate(
            @RequestParam Optional<String> author,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> date) {

        if (author.isPresent() & date.isEmpty()) {
            System.out.println("Ищем посты пользователя с именем " + author);
            return postService.searchPostsByAuthor(author.get());
        }

        if (author.isPresent() & date.isPresent()) {
            System.out.println("Ищем посты пользователя с именем " + author.orElseGet(() -> "not provided") +
                    " и опубликованные " + date.orElseGet(() -> null));
            return postService.searchPostsByAuthorOnLocalDate(author.get(), date.get());
        }

        throw new IncorrectParameterException("author=>" + author + ", date\"yyyy-MM-dd\"=>" + date);
    }
}