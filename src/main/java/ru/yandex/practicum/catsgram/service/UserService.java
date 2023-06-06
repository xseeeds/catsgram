package ru.yandex.practicum.catsgram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;


    public Optional<User> findUserById(String id) {
        return userDao.findUserById(id);
    }
}