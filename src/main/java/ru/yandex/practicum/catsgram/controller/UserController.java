package ru.yandex.practicum.catsgram.controller;


import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.catsgram.modelSchema.Post;
import ru.yandex.practicum.catsgram.modelSchema.User;
import ru.yandex.practicum.catsgram.service.PostService;
import ru.yandex.practicum.catsgram.service.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;


@RestController
@Validated
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/users")
    public Collection<User> findAllUser() {
        return userService.findAllUser();
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/users/{userEmail}")
    public User findUserByEmail(@PathVariable @Email Optional<String> userEmail) {
        if (userEmail.isPresent()) {
            return userService.findUserByEmail(userEmail.get());
        }
        throw new ValidationException("email missing");
    }

    @GetMapping("/users/{userId}/posts/list")
    public Collection<Post> findPostsUserById(
            @PathVariable @Positive long userId,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate to
    ) {
        System.out.println("Ищем посты пользователя " + userId +
                " с даты " + from.toString() + " по дату " + to.toString());
        return postService.listPostsLocalDateFromTo(userId, from, to);
    }


    @GetMapping("/feed")
    public Map<String, Integer> feed() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /feed ещё не реализован.");
    }
}
