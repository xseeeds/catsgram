package ru.yandex.practicum.catsgram.modelSchema;

import java.time.LocalDate;
import java.util.*;

public class User {
    private long id;
    private final String email;
    private final String nickname;
    private final LocalDate birthdate;
    private final Set<Long> postsIdsByUser = new HashSet<>();

    public User(String email, String nickname, LocalDate birthdate) {
        this.email = email;
        this.nickname = nickname;
        this.birthdate = birthdate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addPost(long postId) {
        postsIdsByUser.add(postId);
    }

    public void removePosts(long postId) {
        postsIdsByUser.remove(postId);
    }

    public Set<Long> getPostsIdByUser() {
        return postsIdsByUser;
    }

    public void removeAllPostsByUser() {
        postsIdsByUser.clear();
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email)
                && Objects.equals(nickname, user.nickname)
                && Objects.equals(birthdate, user.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname, birthdate);
    }
}
