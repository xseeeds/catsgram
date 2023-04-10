package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.modelSchema.User;

import java.util.*;

@Service
public class UserService {
    private long globalId = 0;
    private final Map<String, User> emailUser = new HashMap<>();

    public Collection<User> findAllUser() {
        return emailUser.values();
    }

    public User createUser(User user) {
        checkEmail(user);

        if (emailUser.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }
        //log.debug("Текущее количество пользователей : {}", users.size());

        user.setId(getNextId());
        emailUser.put(user.getEmail(), user);
        return user;
    }

    public User updateUser(User user) {
        checkEmail(user);

        if (emailUser.containsKey(user.getEmail())) {
            if (emailUser.get(user.getEmail()).equals(user)) {
                //log.error("Такой пользователь с email: {} уже существует", user);
                throw new UserAlreadyExistException("Такой пользователь уже существует " + user);
            }
        }
        emailUser.put(user.getEmail(), user);

        return user;
    }

    public User findUserByNickName(String nickName) {
        return emailUser
                .values()
                .stream()
                .filter(u -> u.getNickname().equals(nickName))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "Пользователь %s не найден",
                        nickName)));
    }

    public User findUserByEmail(String email) {
        final User user = emailUser.get(email);
        if (user == null) {
            throw new UserNotFoundException(String.format(
                    "Пользователь %s не найден",
                    email));
        }
        return user;
    }

    public User findUserById(long userId) {
        return emailUser
                .values()
                .stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "Пользователь с id=%d не найден",
                        userId)));
    }

    public void removeUserById(int id) {
        emailUser
                .values()
                .stream()
                .filter(user -> user.getId() == id)
                .forEach(user -> {
                    emailUser.remove(user.getEmail());
                    user.removeAllPostsByUser();
                });
    }

    public void removeUserByEmail(String email) {
        findUserByEmail(email).removeAllPostsByUser();
        emailUser.remove(email);
    }

    public void removeAllUser() {
        //
        resetGlobalId();
        emailUser.clear();
    }

    private long getNextId() {
        return ++globalId;
    }

    private void resetGlobalId() {
        globalId = 0;
    }

    private void checkEmail(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
    }
}