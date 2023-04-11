package ru.yandex.practicum.catsgram.dao;

import ru.yandex.practicum.catsgram.modelSchema.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findUserById(String id);
}
