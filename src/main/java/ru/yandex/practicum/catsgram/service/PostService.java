package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.modelSchema.Post;
import ru.yandex.practicum.catsgram.modelSchema.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static ru.yandex.practicum.catsgram.constant.Constant.DESCENDING_ORDER;


@Service
public class PostService {
    private long globalId = 0;
    private final UserService userService;
    private final HashMap<Long, Post> posts = new HashMap<>();

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public Collection<Post> findAllPost(int size, int from, String sort) {
        return posts.values()
                .stream()
                .sorted((p0, p1) -> compare(p0, p1, sort))
                .skip(from)
                .limit(size)
                .collect(toCollection(ArrayList::new));
    }

    public Post create(Post post) {
        User postAuthor = userService.findUserByNickName(post.getAuthor());
        if (postAuthor == null) {
            throw new UserNotFoundException(String.format(
                    "Пользователь %s не найден",
                    post.getAuthor()));
        }

        post.setId(getNextId());
        postAuthor.addPost(post.getId());
        posts.put(post.getId(), post);
        return post;
    }

    public Collection<Post> searchPostsByAuthor(String author) {
        User postAuthor = userService.findUserByNickName(author);

        if (postAuthor == null) {
            throw new UserNotFoundException(String.format(
                    "Пользователь %s не найден",
                    author));
        }

        return postAuthor
                .getPostsIdByUser()
                .stream()
                .map(posts::get)
                .filter(Objects::nonNull)
                .collect(toCollection(ArrayList::new));
    }

    public Collection<Post> findAllPostsByUserEmail(String email, int size, String sort) {
        return userService
                .findUserByEmail(email)
                .getPostsIdByUser()
                .stream()
                .map(posts::get)
                .filter(Objects::nonNull)
                .sorted((p0, p1) -> compare(p0, p1, sort))
                .limit(size)
                .collect(toCollection(ArrayList::new));
    }


    public Post findPostById(long postId) {
        Post post = posts.get(postId);
        if (post != null) {
            return post;
        }
        throw new PostNotFoundException(String.format("Пост № %d не найден", postId));
    }
/*
    public Post findPostById(Integer postId) {
        return posts.stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
    }
*/


    public Collection<Post> searchPostsByAuthorOnLocalDate(String author, LocalDate date) throws UserNotFoundException {
        User postAuthor = userService.findUserByNickName(author);

        return postAuthor
                .getPostsIdByUser()
                .stream()
                .map(posts::get)
                .filter(Objects::nonNull)
                .filter(post -> LocalDate.ofInstant(post.getCreationDate(), ZoneId.systemDefault())
                        .equals(date))
                .collect(toCollection(ArrayList::new));
    }

    public Collection<Post> listPostsLocalDateFromTo(long userId, LocalDate from, LocalDate to) {
        return userService.findUserById(userId)
                .getPostsIdByUser()
                .stream()
                .map(posts::get)
                .filter(Objects::nonNull)
                .filter(post -> !post.getCreationDate().isBefore(from.atStartOfDay(ZoneId.systemDefault()).toInstant())
                                & !post.getCreationDate().isAfter(to.atStartOfDay(ZoneId.systemDefault()).toInstant())
//                        Отрицая, он становится startDate или after.isBefore
//                        Отрицая, он становится endDate или before.isAfter
                )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void removePostById(long id) {
        final User user = userService
                .findUserByNickName(posts
                        .remove(id)
                        .getAuthor());
        user.removePosts(id);
    }

    public void removePost(Post post) {
        final User user = userService
                .findUserByNickName(post.getAuthor());
        posts.remove(post.getId());
        user.removePosts(post.getId());
    }

    public void removeAllPost() {
        userService.findAllUser().forEach(User::removeAllPostsByUser);
        posts.clear();
        resetGlobalId();
    }

    private long getNextId() {
        return ++globalId;
    }

    private void resetGlobalId() {
        globalId = 0;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d?");
    }

    private int compare(Post p0, Post p1, String sort) {
        int result = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
        if (sort.equals(DESCENDING_ORDER)) {
            result = -1 * result; //обратный порядок сортировки
        }
        return result;
    }
}
